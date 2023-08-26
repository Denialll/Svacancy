<#assign known = SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.getUsername()
    chatMessages = user.getChatRooms()
    isActive = user.isActive()
    isAdmin = user.isAdmin()
    isCreator = user.isCreator()
    isHR = user.isHR()
    isUser = user.isUser()
    userId = user.getId()
    roomId = ""
    >
<#else>
    <#assign
    username = ""
    isUser = false
    isHR = false
    isAdmin = false
    userId = -1
    >
</#if>