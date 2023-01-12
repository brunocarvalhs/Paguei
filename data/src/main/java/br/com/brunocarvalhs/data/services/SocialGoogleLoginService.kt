package br.com.brunocarvalhs.data.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import br.com.brunocarvalhs.payflow.domain.entities.UserEntities
import br.com.brunocarvalhs.payflow.domain.services.AuthService
import br.com.brunocarvalhs.payflow.domain.services.SocialGoogleLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SocialGoogleLoginService @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val authService: AuthService<AuthCredential>
) : SocialGoogleLogin {

    private val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("")
            .requestEmail().requestProfile().requestId().build()

    private val mGoogleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(applicationContext, gso)


    override fun <T> signIn(context: T) {
        val signInIntent = mGoogleSignInClient.signInIntent

        when (context) {
            is Fragment -> context.startActivityForResult(signInIntent, REQUEST_CODE)
            is Activity -> context.startActivityForResult(signInIntent, REQUEST_CODE)
        }
    }

    override suspend fun <T> onResult(requestCode: Int, data: T?): Result<UserEntities> =
        withContext(Dispatchers.IO) {
            try {
                if (requestCode == REQUEST_CODE) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(data as Intent)
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    val result = authService.login(credential)
                    return@withContext result?.let { Result.success(result) } ?: Result.failure(
                        Exception("")
                    )
                }
                return@withContext Result.failure(Exception(""))
            } catch (error: Exception) {
                return@withContext Result.failure(error)
            }
        }

    private companion object {
        const val REQUEST_CODE = 2
    }
}