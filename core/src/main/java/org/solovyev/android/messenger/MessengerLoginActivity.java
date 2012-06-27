package org.solovyev.android.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.android.ActivityDestroyerController;
import org.solovyev.android.Captcha;
import org.solovyev.android.ResolvedCaptcha;
import org.solovyev.android.messenger.api.ApiError;
import org.solovyev.android.messenger.api.ApiResponseErrorException;
import org.solovyev.android.messenger.security.LoginUserAsyncTask;
import org.solovyev.android.messenger.users.MessengerFriendsActivity;
import org.solovyev.android.messenger.view.CaptchaViewBuilder;
import org.solovyev.android.view.ViewFromLayoutBuilder;
import org.solovyev.common.utils.StringUtils;

/**
 * User: serso
 * Date: 5/24/12
 * Time: 10:15 PM
 */
public class MessengerLoginActivity extends Activity implements CaptchaViewBuilder.CaptchaEnteredListener {

    public static void startActivity(@NotNull Activity activity) {
        final Intent result = new Intent();
        result.setClass(activity, MessengerLoginActivity.class);
        activity.startActivity(result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.msg_main);

        final ViewGroup content = (ViewGroup) findViewById(R.id.content);
        content.addView(ViewFromLayoutBuilder.newInstance(R.layout.msg_login).build(this), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        final Button registerButton = (Button) content.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessengerRegistrationActivity.startActivity(MessengerLoginActivity.this);
            }
        });


        final Button loginButton = (Button) content.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToLogin(null);
            }
        });

    }

    private void tryToLogin(@Nullable ResolvedCaptcha resolvedCaptcha) {
        final EditText loginInput = (EditText) this.findViewById(R.id.login);
        final EditText passwordInput = (EditText) this.findViewById(R.id.password);

        final String login = loginInput.getText().toString();
        final String password = passwordInput.getText().toString();

        new LoginUserAsyncTask(this) {

            @Override
            protected void onSuccessPostExecute(@Nullable Void result) {
                super.onSuccessPostExecute(result);
                MessengerFriendsActivity.startActivity(MessengerLoginActivity.this);
            }

            @Override
            protected void onFailurePostExecute(@NotNull Exception e) {
                if (e instanceof ApiResponseErrorException) {
                    final ApiError apiError = ((ApiResponseErrorException) e).getApiError();
                    final Captcha captcha = apiError.getCaptcha();
                    if (captcha == null) {
                        final String errorDescription = apiError.getErrorDescription();
                        if (!StringUtils.isEmpty(errorDescription)) {
                            Toast.makeText(MessengerLoginActivity.this, errorDescription, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MessengerLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        new CaptchaViewBuilder(MessengerLoginActivity.this, captcha, MessengerLoginActivity.this).build().show();
                    }
                } else {
                    super.onFailurePostExecute(e);
                }
            }
        }.execute(new LoginUserAsyncTask.Input(login, password, resolvedCaptcha));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityDestroyerController.getInstance().fireActivityDestroyed(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (MessengerConfigurationImpl.getInstance().getServiceLocator().getAuthServiceFacade().isUserLoggedIn()) {
            MessengerFriendsActivity.startActivity(this);
        }
    }

    @Override
    public void onCaptchaEntered(@NotNull ResolvedCaptcha resolvedCaptcha) {
        tryToLogin(resolvedCaptcha);
    }
}