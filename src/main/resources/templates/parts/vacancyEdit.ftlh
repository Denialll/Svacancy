<#include "security.ftlh">
<#macro vacancyEdit path>

<#--    <#if isHR>-->
        <p>
            <button class="btn btn-primary mt-3" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseExample"
                    aria-expanded="false" aria-controls="collapseExample">
                <#if path == "/vacancyCreate">
                    Vacancy creator
                <#else>
                    Vacancy editor
                </#if>
            </button>
        </p>

        <div class="collapse <#if vacancy??>show</#if>" id="collapseExample">
            <form method="post" enctype="multipart/form-data">
                <div class="input-group has-validation mb-3">
                    <input class="form-control ${(titleError??)?string('is-invalid', '')}"
                           value="<#if vacancy??>${vacancy.title}</#if>" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-default" type="text" name="title"
                           placeholder="Vacancy title"/>
                    <#if titleError??>
                        <div class="invalid-feedback">
                            ${titleError}
                        </div>
                    </#if>
                </div>
                <div class="input-group has-validation mb-3">
                    <input class="form-control ${(INNError??)?string('is-invalid', '')}"
                           value="<#if company??>${company.INN}</#if>" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-default" type="text" name="description"
                           placeholder="Description of vacancy"/>
                    <#if INNError??>
                        <div class="invalid-feedback">
                            ${INNError}
                        </div>
                    </#if>
                </div>
                <div class="input-group has-validation mb-3">
                    <span class="input-group-text">From:</span>
                    <input class="form-control ${(salaryError??)?string('is-invalid', '')}"
                           value="<#if vacancy??>${vacancy.salary}</#if>" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-default" type="text" name="salaryFrom"
                           />
                    <#if salaryError??>
                        <div class="invalid-feedback">
                            ${salaryError}
                        </div>
                    </#if>
                    <span class="input-group-text">₽</span>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text">To:</span>
                    <input type="text" class="form-control" name="salaryTo">
                    <span class="input-group-text">₽</span>
                </div>
                <div class="input-group has-validation mb-3">
                    <input class="form-control ${(tagError??)?string('is-invalid', '')}"
                           value="<#if vacancy??>${vacancy.tag}</#if>" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-default" type="text" name="tag" placeholder="Tag"/>
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="input-group mb-3">
                    <input class="form-control" aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-default" id="customFile"
                           type="file" name="file"/>
                    <#--            <label class="custom-file-label" for="customFile">Choose file</label>-->
                </div>
                <input type="hidden" name="id" value="<#if vacancy??>${vacancy.id}<#else>0</#if>"/>
                <button type="submit" class="btn btn-info">Save vacancy</button>
            </form>
        </div>

<#--    </#if>-->
</#macro>