<#macro passwordrecovery path isPasswordChangeForm>
<#--    value="<#if user??>${user.text}</#if>"-->
    <head>
        <link rel="stylesheet" href="/static/css/login-register.css">
    </head>
    <body-login-register>
        <div class="wrapper">
            <form action="${path}" method="post">
                <#if !isPasswordChangeForm>
                    <#if MessageForUser??>
                        <h5>An email has been sent to your email address with instructions on how to reset your
                            password. If you don't receive it within a few minutes, make sure you have provided the
                            email address
                            for your Svacancy account and try again or contact us for help.</h5>
                    <#else>
                        <h1>Enter your email</h1>
                        <div class="input-box">
                            <input class="form-control ${(emailError??)?string('is-invalid', '')}" type="email"
                                   name="email"
                                   value="<#if user??>${user.email}</#if>"
                                   class="form-control" placeholder="youreMail@mail.com"/>
                            <#if emailError??>
                                <div class="invalid-feedback">
                                    ${emailError}
                                </div>
                            </#if>
                            <i class='bx bxs-envelope'></i>
                        </div>
                    </#if>
                </#if>
                <#if isPasswordChangeForm>
                    <h1>Enter your new password</h1>
                    <div class="input-box">
                        <input class="form-control ${(passwordError??)?string('is-invalid', '')}" type="password"
                               name="password" class="form-control" placeholder="Password"/>
                        <#if passwordError??>
                            <div class="invalid-feedback">
                                ${passwordError}
                            </div>
                        </#if>
                        <i class='bx bxs-lock-alt'></i>
                    </div>
                    <div class="input-box">
                        <input class="form-control ${(password2Error??)?string('is-invalid', '')}" type="password"
                               name="password2" placeholder="Retype Password"/>
                        <#if password2Error??>
                            <div class="invalid-feedback">
                                ${password2Error}
                            </div>
                        </#if>
                        <i class='bx bxs-lock-alt'></i>
                    </div>
                </#if>
                <#if MessageForUser??>
                    <a type="button" class="button"
                       href="/login"> Login </a>
                <#else>
                    <button type="submit" class="button">Send</button>
                </#if>
            </form>
        </div>
    </body-login-register>
</#macro>
