<#import "parts/common.ftlh" as c>
<#import "parts/vacancyEdit.ftlh" as messageEdit>

<@c.page>

    <div class="container md-6 mt-5">

        <form method="get" action="/main" class="form-inline">
            <div class="input-group">
                <input type="text" name="filter" class="form-control width10" style="border-radius: 5px;"
                       value="${filter?ifExists}"
                       placeholder="Search by tag"/>
                <span class="input-group-btn">
                <button type="submit" class="btn btn-primary ms-1">Search</button>
                </span>
            </div>
        </form>

        <@messageEdit.vacancyEdit "/vacancyCreate"/>
        <#include "parts/vacancyList.ftlh">


<#--        <button type="button" class="btn btn-primary" id="liveToastBtn">Show live toast</button>-->
<#--        <div class="toast-container position-fixed bottom-0 end-0 p-3 border-0 show">-->
<#--            <div id="liveToast" class="toast bg-primary" role="alert" aria-live="assertive" aria-atomic="true">-->

<#--                <div class="toast-body">-->
<#--                    Hello, world! This is a toast message.-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->

    </div>
</@c.page>

<script>
/*    const toastTrigger = document.getElementById('liveToastBtn')
    const toastLiveExample = document.getElementById('liveToast')

    if (toastTrigger) {
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
        toastTrigger.addEventListener('click', () => {
            toastBootstrap.show()
        })
    }*/
</script>
