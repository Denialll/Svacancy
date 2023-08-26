<#import "parts/common.ftlh" as c>
<#import "parts/passwordrecovery.ftlh" as p>

<@c.page>
    <@p.passwordrecovery "/change-password/${code}" true/>
</@c.page>