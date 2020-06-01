<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDReferenceList.home.createOrEditLabel" v-text="$t('opusWebApp.aDReferenceList.home.createOrEditLabel')">Create or edit a ADReferenceList</h2>
                <div>
                    <div class="form-group" v-if="aDReferenceList.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDReferenceList.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.name')" for="ad-reference-list-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-reference-list-name"
                            :class="{'valid': !$v.aDReferenceList.name.$invalid, 'invalid': $v.aDReferenceList.name.$invalid }" v-model="$v.aDReferenceList.name.$model"  required/>
                        <div v-if="$v.aDReferenceList.name.$anyDirty && $v.aDReferenceList.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDReferenceList.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.value')" for="ad-reference-list-value">Value</label>
                        <input type="text" class="form-control" name="value" id="ad-reference-list-value"
                            :class="{'valid': !$v.aDReferenceList.value.$invalid, 'invalid': $v.aDReferenceList.value.$invalid }" v-model="$v.aDReferenceList.value.$model"  required/>
                        <div v-if="$v.aDReferenceList.value.$anyDirty && $v.aDReferenceList.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDReferenceList.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.description')" for="ad-reference-list-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-reference-list-description"
                            :class="{'valid': !$v.aDReferenceList.description.$invalid, 'invalid': $v.aDReferenceList.description.$invalid }" v-model="$v.aDReferenceList.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.active')" for="ad-reference-list-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-reference-list-active"
                            :class="{'valid': !$v.aDReferenceList.active.$invalid, 'invalid': $v.aDReferenceList.active.$invalid }" v-model="$v.aDReferenceList.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.adOrganization')" for="ad-reference-list-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-reference-list-adOrganization" name="adOrganization" v-model="$v.aDReferenceList.adOrganizationId.$model" required>
                            <option v-if="!aDReferenceList.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDReferenceList.adOrganizationId.$anyDirty && $v.aDReferenceList.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDReferenceList.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReferenceList.adReference')" for="ad-reference-list-adReference">Ad Reference</label>
                        <select class="form-control" id="ad-reference-list-adReference" name="adReference" v-model="$v.aDReferenceList.adReferenceId.$model" required>
                            <option v-if="!aDReferenceList.adReferenceId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDReferenceOption.id" v-for="aDReferenceOption in aDReferences" :key="aDReferenceOption.id">{{aDReferenceOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDReferenceList.adReferenceId.$anyDirty && $v.aDReferenceList.adReferenceId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDReferenceList.adReferenceId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDReferenceList.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-reference-list-update.component.ts">
</script>
