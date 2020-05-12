<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDColumn.home.createOrEditLabel" v-text="$t('opusWebApp.aDColumn.home.createOrEditLabel')">Create or edit a ADColumn</h2>
                <div>
                    <div class="form-group" v-if="aDColumn.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDColumn.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.name')" for="ad-column-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-column-name"
                            :class="{'valid': !$v.aDColumn.name.$invalid, 'invalid': $v.aDColumn.name.$invalid }" v-model="$v.aDColumn.name.$model"  required/>
                        <div v-if="$v.aDColumn.name.$anyDirty && $v.aDColumn.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDColumn.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.sqlName')" for="ad-column-sqlName">Sql Name</label>
                        <input type="text" class="form-control" name="sqlName" id="ad-column-sqlName"
                            :class="{'valid': !$v.aDColumn.sqlName.$invalid, 'invalid': $v.aDColumn.sqlName.$invalid }" v-model="$v.aDColumn.sqlName.$model"  required/>
                        <div v-if="$v.aDColumn.sqlName.$anyDirty && $v.aDColumn.sqlName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDColumn.sqlName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.description')" for="ad-column-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-column-description"
                            :class="{'valid': !$v.aDColumn.description.$invalid, 'invalid': $v.aDColumn.description.$invalid }" v-model="$v.aDColumn.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.fieldLength')" for="ad-column-fieldLength">Field Length</label>
                        <input type="number" class="form-control" name="fieldLength" id="ad-column-fieldLength"
                            :class="{'valid': !$v.aDColumn.fieldLength.$invalid, 'invalid': $v.aDColumn.fieldLength.$invalid }" v-model.number="$v.aDColumn.fieldLength.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.key')" for="ad-column-key">Key</label>
                        <input type="checkbox" class="form-check" name="key" id="ad-column-key"
                            :class="{'valid': !$v.aDColumn.key.$invalid, 'invalid': $v.aDColumn.key.$invalid }" v-model="$v.aDColumn.key.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.type')" for="ad-column-type">Type</label>
                        <select class="form-control" name="type" :class="{'valid': !$v.aDColumn.type.$invalid, 'invalid': $v.aDColumn.type.$invalid }" v-model="$v.aDColumn.type.$model" id="ad-column-type" >
                            <option value="STRING" v-bind:label="$t('opusWebApp.ADColumnType.STRING')">STRING</option>
                            <option value="INTEGER" v-bind:label="$t('opusWebApp.ADColumnType.INTEGER')">INTEGER</option>
                            <option value="LONG" v-bind:label="$t('opusWebApp.ADColumnType.LONG')">LONG</option>
                            <option value="BIG_DECIMAL" v-bind:label="$t('opusWebApp.ADColumnType.BIG_DECIMAL')">BIG_DECIMAL</option>
                            <option value="FLOAT" v-bind:label="$t('opusWebApp.ADColumnType.FLOAT')">FLOAT</option>
                            <option value="DOUBLE" v-bind:label="$t('opusWebApp.ADColumnType.DOUBLE')">DOUBLE</option>
                            <option value="BOOLEAN" v-bind:label="$t('opusWebApp.ADColumnType.BOOLEAN')">BOOLEAN</option>
                            <option value="LOCAL_DATE" v-bind:label="$t('opusWebApp.ADColumnType.LOCAL_DATE')">LOCAL_DATE</option>
                            <option value="ZONED_DATE_TIME" v-bind:label="$t('opusWebApp.ADColumnType.ZONED_DATE_TIME')">ZONED_DATE_TIME</option>
                            <option value="DURATION" v-bind:label="$t('opusWebApp.ADColumnType.DURATION')">DURATION</option>
                            <option value="INSTANT" v-bind:label="$t('opusWebApp.ADColumnType.INSTANT')">INSTANT</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.foreignKey')" for="ad-column-foreignKey">Foreign Key</label>
                        <input type="checkbox" class="form-check" name="foreignKey" id="ad-column-foreignKey"
                            :class="{'valid': !$v.aDColumn.foreignKey.$invalid, 'invalid': $v.aDColumn.foreignKey.$invalid }" v-model="$v.aDColumn.foreignKey.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.importedTable')" for="ad-column-importedTable">Imported Table</label>
                        <input type="text" class="form-control" name="importedTable" id="ad-column-importedTable"
                            :class="{'valid': !$v.aDColumn.importedTable.$invalid, 'invalid': $v.aDColumn.importedTable.$invalid }" v-model="$v.aDColumn.importedTable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.importedColumn')" for="ad-column-importedColumn">Imported Column</label>
                        <input type="text" class="form-control" name="importedColumn" id="ad-column-importedColumn"
                            :class="{'valid': !$v.aDColumn.importedColumn.$invalid, 'invalid': $v.aDColumn.importedColumn.$invalid }" v-model="$v.aDColumn.importedColumn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.mandatory')" for="ad-column-mandatory">Mandatory</label>
                        <input type="checkbox" class="form-check" name="mandatory" id="ad-column-mandatory"
                            :class="{'valid': !$v.aDColumn.mandatory.$invalid, 'invalid': $v.aDColumn.mandatory.$invalid }" v-model="$v.aDColumn.mandatory.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.mandatoryLogic')" for="ad-column-mandatoryLogic">Mandatory Logic</label>
                        <input type="text" class="form-control" name="mandatoryLogic" id="ad-column-mandatoryLogic"
                            :class="{'valid': !$v.aDColumn.mandatoryLogic.$invalid, 'invalid': $v.aDColumn.mandatoryLogic.$invalid }" v-model="$v.aDColumn.mandatoryLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.readOnlyLogic')" for="ad-column-readOnlyLogic">Read Only Logic</label>
                        <input type="text" class="form-control" name="readOnlyLogic" id="ad-column-readOnlyLogic"
                            :class="{'valid': !$v.aDColumn.readOnlyLogic.$invalid, 'invalid': $v.aDColumn.readOnlyLogic.$invalid }" v-model="$v.aDColumn.readOnlyLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.updatable')" for="ad-column-updatable">Updatable</label>
                        <input type="checkbox" class="form-check" name="updatable" id="ad-column-updatable"
                            :class="{'valid': !$v.aDColumn.updatable.$invalid, 'invalid': $v.aDColumn.updatable.$invalid }" v-model="$v.aDColumn.updatable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.defaultValue')" for="ad-column-defaultValue">Default Value</label>
                        <input type="text" class="form-control" name="defaultValue" id="ad-column-defaultValue"
                            :class="{'valid': !$v.aDColumn.defaultValue.$invalid, 'invalid': $v.aDColumn.defaultValue.$invalid }" v-model="$v.aDColumn.defaultValue.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.formatPattern')" for="ad-column-formatPattern">Format Pattern</label>
                        <input type="text" class="form-control" name="formatPattern" id="ad-column-formatPattern"
                            :class="{'valid': !$v.aDColumn.formatPattern.$invalid, 'invalid': $v.aDColumn.formatPattern.$invalid }" v-model="$v.aDColumn.formatPattern.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.minLength')" for="ad-column-minLength">Min Length</label>
                        <input type="number" class="form-control" name="minLength" id="ad-column-minLength"
                            :class="{'valid': !$v.aDColumn.minLength.$invalid, 'invalid': $v.aDColumn.minLength.$invalid }" v-model.number="$v.aDColumn.minLength.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.maxLength')" for="ad-column-maxLength">Max Length</label>
                        <input type="number" class="form-control" name="maxLength" id="ad-column-maxLength"
                            :class="{'valid': !$v.aDColumn.maxLength.$invalid, 'invalid': $v.aDColumn.maxLength.$invalid }" v-model.number="$v.aDColumn.maxLength.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.minValue')" for="ad-column-minValue">Min Value</label>
                        <input type="number" class="form-control" name="minValue" id="ad-column-minValue"
                            :class="{'valid': !$v.aDColumn.minValue.$invalid, 'invalid': $v.aDColumn.minValue.$invalid }" v-model.number="$v.aDColumn.minValue.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.maxValue')" for="ad-column-maxValue">Max Value</label>
                        <input type="number" class="form-control" name="maxValue" id="ad-column-maxValue"
                            :class="{'valid': !$v.aDColumn.maxValue.$invalid, 'invalid': $v.aDColumn.maxValue.$invalid }" v-model.number="$v.aDColumn.maxValue.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.active')" for="ad-column-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-column-active"
                            :class="{'valid': !$v.aDColumn.active.$invalid, 'invalid': $v.aDColumn.active.$invalid }" v-model="$v.aDColumn.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.adClient')" for="ad-column-adClient">Ad Client</label>
                        <select class="form-control" id="ad-column-adClient" name="adClient" v-model="$v.aDColumn.adClientId.$model" required>
                            <option v-if="!aDColumn.adClientId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDClientOption.id" v-for="aDClientOption in aDClients" :key="aDClientOption.id">{{aDClientOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDColumn.adClientId.$anyDirty && $v.aDColumn.adClientId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDColumn.adClientId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.adOrganization')" for="ad-column-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-column-adOrganization" name="adOrganization" v-model="$v.aDColumn.adOrganizationId.$model" required>
                            <option v-if="!aDColumn.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDColumn.adOrganizationId.$anyDirty && $v.aDColumn.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDColumn.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.adReference')" for="ad-column-adReference">Ad Reference</label>
                        <select class="form-control" id="ad-column-adReference" name="adReference" v-model="aDColumn.adReferenceId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="aDReferenceOption.id" v-for="aDReferenceOption in aDReferences" :key="aDReferenceOption.id">{{aDReferenceOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDColumn.adTable')" for="ad-column-adTable">Ad Table</label>
                        <select class="form-control" id="ad-column-adTable" name="adTable" v-model="$v.aDColumn.adTableId.$model" required>
                            <option v-if="!aDColumn.adTableId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDTableOption.id" v-for="aDTableOption in aDTables" :key="aDTableOption.id">{{aDTableOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDColumn.adTableId.$anyDirty && $v.aDColumn.adTableId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDColumn.adTableId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDColumn.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-column-update.component.ts">
</script>
