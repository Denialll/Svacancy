<#assign known = SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.getUsername()
<#--    chatMessages = user.getChatRooms()-->
    isActive = user.isActive()
    isCompanyActive = user.isCompanyActive()
    isAdmin = user.isAdmin()
    isCreator = user.isCreator()
    isHR = user.isHR()
    isUser = user.isUser()
    isEmployed = user.isEmployed()
    companyId = user.getCompanyCreatorId()
    userId = user.getId()
    roomId = ""
    >
<#else>
    <#assign
    username = ""
    isUser = false
    isHR = false
    isAdmin = false
    isEmployed = false
    isCreator = false
    isCompanyActive = false
    companyId = -1
    userId = -1
    >
</#if>