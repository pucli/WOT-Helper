const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp({
  credential: admin.credential.cert('./wot-helper-edc0c-firebase-adminsdk-4r71e-647f4e408a.json')
});
const axios = require('axios');

// Store application ID in .env
const APPLICATION_ID = process.env.WOT_APPLICATION_ID;

exports.handleWargamingRedirect = functions.https.onRequest(async (req, res) => {
  // Support both GET (query) and POST (body)
  const params = req.method === 'GET' ? req.query : req.body;

  functions.logger.info('handleWargamingRedirect called', { received: params });

  if (params.status === 'error') {
    const { code, message } = params;
    functions.logger.warn('Wargaming authentication failed', { code, message });
    return res.status(401).json({ error: `Wargaming auth failed: ${message || 'Unknown error'}`, code });
  }

  const { access_token, account_id, nickname } = params;
  if (!access_token || !account_id || !nickname) {
    return res.status(400).json({ error: 'Missing parameters' });
  }

  try {
    const verifyUrl = `https://api.worldoftanks.eu/wot/account/info/?application_id=${APPLICATION_ID}&account_id=${account_id}&access_token=${access_token}`;
    const response = await axios.get(verifyUrl);
    if (response.data.status !== 'ok') {
      throw new Error('Invalid Wargaming token');
    }

    const userData = response.data.data[account_id];
    if (!userData) {
      throw new Error('User data not found');
    }

    functions.logger.info('Wargaming token verified successfully', { nickname });

    const uid = `${account_id}`;
    const email = `${nickname}@wot-helper.test`;

    // Update or create the user in Firebase Authentication
    try {
      await admin.auth().updateUser(uid, {
        displayName: nickname,
        email: email,
        emailVerified: true
      });
    } catch (error) {
      if (error.code === 'auth/user-not-found') {
        await admin.auth().createUser({
          uid: uid,
          displayName: nickname,
          email: email,
          emailVerified: true
        });
      } else {
        throw error;
      }
    }

    const customToken = await admin.auth().createCustomToken(uid, {
      nickname: nickname,
      account_id: account_id,
      provider: 'wargaming'
    });

    // Redirect to the app with the custom token
    const redirectUri = `wot-helper://callback?custom_token=${encodeURIComponent(customToken)}`;
    res.redirect(302, redirectUri);
  } catch (error) {
    functions.logger.error('handleWargamingRedirect error', { error: error.message });
    return res.status(500).json({ error: 'Verification failed', details: error.message });
  }
});