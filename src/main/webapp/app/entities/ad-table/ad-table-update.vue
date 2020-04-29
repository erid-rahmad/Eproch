<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDTable.home.createOrEditLabel" v-text="$t('opusWebApp.aDTable.home.createOrEditLabel')">Create or edit a ADTable</h2>
                <div>
                    <div class="form-group" v-if="aDTable.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDTable.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTable.name')" for="ad-table-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-table-name"
                            :class="{'valid': !$v.aDTable.name.$invalid, 'invalid': $v.aDTable.name.$invalid }" v-model="$v.aDTable.name.$model"  required/>
                        <div v-if="$v.aDTable.name.$anyDirty && $v.aDTable.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDTable.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTable.view')" for="ad-table-view">View</label>
                        <input type="checkbox" class="form-check" name="view" id="ad-table-view"
                            :class="{'valid': !$v.aDTable.view.$invalid, 'invalid': $v.aDTable.view.$invalid }" v-model="$v.aDTable.view.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTable.active')" for="ad-table-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-table-active"
                            :class="{'valid': !$v.aDTable.active.$invalid, 'invalid': $v.aDTable.active.$invalid }" v-model="$v.aDTable.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTable.adClient')" for="ad-table-adClient">Ad Client</label>
                        <select class="form-control" id="ad-table-adClient" name="adClient" v-model="$v.aDTable.adClientId.$model" required>
                            <option v-if="!aDTable.adClientId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDClientOption.id" v-for="aDClientOption in aDClients" :key="aDClientOption.id">{{aDClientOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTable.adClientId.$anyDirty && $v.aDTable.adClientId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTable.adClientId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDTable.adOrganization')" for="ad-table-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-table-adOrganization" name="adOrganization" v-model="$v.aDTable.adOrganizationId.$model" required>
                            <option v-if="!aDTable.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDTable.adOrganizationId.$anyDirty && $v.aDTable.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDTable.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDTable.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-table-update.component.ts">
</script>
