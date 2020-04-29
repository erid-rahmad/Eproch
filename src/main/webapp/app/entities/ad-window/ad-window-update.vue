<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDWindow.home.createOrEditLabel" v-text="$t('opusWebApp.aDWindow.home.createOrEditLabel')">Create or edit a ADWindow</h2>
                <div>
                    <div class="form-group" v-if="aDWindow.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDWindow.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.name')" for="ad-window-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-window-name"
                            :class="{'valid': !$v.aDWindow.name.$invalid, 'invalid': $v.aDWindow.name.$invalid }" v-model="$v.aDWindow.name.$model"  required/>
                        <div v-if="$v.aDWindow.name.$anyDirty && $v.aDWindow.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDWindow.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.description')" for="ad-window-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-window-description"
                            :class="{'valid': !$v.aDWindow.description.$invalid, 'invalid': $v.aDWindow.description.$invalid }" v-model="$v.aDWindow.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.type')" for="ad-window-type">Type</label>
                        <select class="form-control" name="type" :class="{'valid': !$v.aDWindow.type.$invalid, 'invalid': $v.aDWindow.type.$invalid }" v-model="$v.aDWindow.type.$model" id="ad-window-type"  required>
                            <option value="MAINTAIN" v-bind:label="$t('opusWebApp.ADWindowType.MAINTAIN')">MAINTAIN</option>
                            <option value="QUERY" v-bind:label="$t('opusWebApp.ADWindowType.QUERY')">QUERY</option>
                            <option value="TRANSACTION" v-bind:label="$t('opusWebApp.ADWindowType.TRANSACTION')">TRANSACTION</option>
                        </select>
                        <div v-if="$v.aDWindow.type.$anyDirty && $v.aDWindow.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDWindow.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.active')" for="ad-window-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-window-active"
                            :class="{'valid': !$v.aDWindow.active.$invalid, 'invalid': $v.aDWindow.active.$invalid }" v-model="$v.aDWindow.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.adClient')" for="ad-window-adClient">Ad Client</label>
                        <select class="form-control" id="ad-window-adClient" name="adClient" v-model="$v.aDWindow.adClientId.$model" required>
                            <option v-if="!aDWindow.adClientId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDClientOption.id" v-for="aDClientOption in aDClients" :key="aDClientOption.id">{{aDClientOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDWindow.adClientId.$anyDirty && $v.aDWindow.adClientId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDWindow.adClientId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDWindow.adOrganization')" for="ad-window-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-window-adOrganization" name="adOrganization" v-model="$v.aDWindow.adOrganizationId.$model" required>
                            <option v-if="!aDWindow.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDWindow.adOrganizationId.$anyDirty && $v.aDWindow.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDWindow.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDWindow.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-window-update.component.ts">
</script>
