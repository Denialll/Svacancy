<#macro login path isRegisterForm>
<#--    value="<#if user??>${user.text}</#if>"-->
<head>
    <link rel="stylesheet" href="/static/css/login-register.css">
</head>
    <body-login-register>
        <div class="wrapper">
            <form action="${path}" method="post">
                <#if path == "/forgotpassword">
                    <h1>Enter your email</h1>
                </#if>
                <#if !isRegisterForm>
                    <h1>Login</h1>
                <#else>
                    <h1>Registration</h1>
                </#if>
                <div class="input-box">
                    <input class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.username}</#if>"
                           type="text" name="username"
                           placeholder="Username"/>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                    <i class='bx bxs-user'></i>
                </div>
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
                <#if !isRegisterForm>
                    <#if errorMessage??>
                        <div class="alert alert-danger" role="alert">
                            ${errorMessage}
                        </div>
                    </#if>
                    <#if message??>
                        <div class="alert alert-${messageType}" role="alert">
                            ${message}
                        </div>
                    </#if>
                </#if>
                <#if !isRegisterForm>
                    <div class="remember-forgot">
                        <label><input type="checkbox">Remember me</label>
                        <a href="/forgot-password">Forgot password?</a>
                    </div>
                    <div class="register-link">
                        <p>Don't have an account?<a href="/registration"> Register now!</a></p>
                    </div>
                </#if>
                <#if isRegisterForm>
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
                    <div class="col-sm-6 mb-2 ms-3">
                        <div class="g-recaptcha" data-sitekey="6Ldfdv4mAAAAANqnkYg-SgkguRwotH0-txxZQ3GR"></div>
                        <#if captchaError??>
                            <div class="alert alert-danger" role="alert">
                                ${captchaError}
                            </div>
                        </#if>
                    </div>
                </#if>
                <#if !isRegisterForm>
                    <button type="submit" class="button">Login</button>
                <#else>
                    <button type="submit" class="button">Register</button>
                </#if>
            </form>
        </div>
    </body-login-register>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button class="btn btn-primary me-3"  type="submit"> Sign out</button>
    </form>
</#macro>