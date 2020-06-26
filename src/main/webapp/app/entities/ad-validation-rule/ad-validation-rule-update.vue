<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.adValidationRule.home.createOrEditLabel" v-text="$t('opusWebApp.adValidationRule.home.createOrEditLabel')">Create or edit a AdValidationRule</h2>
                <div>
                    <div class="form-group" v-if="adValidationRule.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="adValidationRule.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.uid')" for="ad-validation-rule-uid">Uid</label>
                        <input type="text" class="form-control" name="uid" id="ad-validation-rule-uid"
                            :class="{'valid': !$v.adValidationRule.uid.$invalid, 'invalid': $v.adValidationRule.uid.$invalid }" v-model="$v.adValidationRule.uid.$model"  required/>
                        <div v-if="$v.adValidationRule.uid.$anyDirty && $v.adValidationRule.uid.$invalid">
                            <small class="form-text text-danger" v-if="!$v.adValidationRule.uid.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.name')" for="ad-validation-rule-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-validation-rule-name"
                            :class="{'valid': !$v.adValidationRule.name.$invalid, 'invalid': $v.adValidationRule.name.$invalid }" v-model="$v.adValidationRule.name.$model"  required/>
                        <div v-if="$v.adValidationRule.name.$anyDirty && $v.adValidationRule.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.adValidationRule.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.description')" for="ad-validation-rule-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-validation-rule-description"
                            :class="{'valid': !$v.adValidationRule.description.$invalid, 'invalid': $v.adValidationRule.description.$invalid }" v-model="$v.adValidationRule.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.query')" for="ad-validation-rule-query">Query</label>
                        <input type="text" class="form-control" name="query" id="ad-validation-rule-query"
                            :class="{'valid': !$v.adValidationRule.query.$invalid, 'invalid': $v.adValidationRule.query.$invalid }" v-model="$v.adValidationRule.query.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.active')" for="ad-validation-rule-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-validation-rule-active"
                            :class="{'valid': !$v.adValidationRule.active.$invalid, 'invalid': $v.adValidationRule.active.$invalid }" v-model="$v.adValidationRule.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.adValidationRule.adOrganization')" for="ad-validation-rule-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-validation-rule-adOrganization" name="adOrganization" v-model="$v.adValidationRule.adOrganizationId.$model" required>
                            <option v-if="!adValidationRule.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.adValidationRule.adOrganizationId.$anyDirty && $v.adValidationRule.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.adValidationRule.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.adValidationRule.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-validation-rule-update.component.ts">
</script>
