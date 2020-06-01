<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDOrganization.home.createOrEditLabel" v-text="$t('opusWebApp.aDOrganization.home.createOrEditLabel')">Create or edit a ADOrganization</h2>
                <div>
                    <div class="form-group" v-if="aDOrganization.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDOrganization.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDOrganization.name')" for="ad-organization-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-organization-name"
                            :class="{'valid': !$v.aDOrganization.name.$invalid, 'invalid': $v.aDOrganization.name.$invalid }" v-model="$v.aDOrganization.name.$model"  required/>
                        <div v-if="$v.aDOrganization.name.$anyDirty && $v.aDOrganization.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDOrganization.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDOrganization.code')" for="ad-organization-code">Code</label>
                        <input type="text" class="form-control" name="code" id="ad-organization-code"
                            :class="{'valid': !$v.aDOrganization.code.$invalid, 'invalid': $v.aDOrganization.code.$invalid }" v-model="$v.aDOrganization.code.$model"  required/>
                        <div v-if="$v.aDOrganization.code.$anyDirty && $v.aDOrganization.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDOrganization.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDOrganization.description')" for="ad-organization-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-organization-description"
                            :class="{'valid': !$v.aDOrganization.description.$invalid, 'invalid': $v.aDOrganization.description.$invalid }" v-model="$v.aDOrganization.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDOrganization.active')" for="ad-organization-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-organization-active"
                            :class="{'valid': !$v.aDOrganization.active.$invalid, 'invalid': $v.aDOrganization.active.$invalid }" v-model="$v.aDOrganization.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDOrganization.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-organization-update.component.ts">
</script>
