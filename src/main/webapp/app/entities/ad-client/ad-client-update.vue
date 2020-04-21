<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDClient.home.createOrEditLabel" v-text="$t('opusWebApp.aDClient.home.createOrEditLabel')">Create or edit a ADClient</h2>
                <div>
                    <div class="form-group" v-if="aDClient.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDClient.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDClient.name')" for="ad-client-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-client-name"
                            :class="{'valid': !$v.aDClient.name.$invalid, 'invalid': $v.aDClient.name.$invalid }" v-model="$v.aDClient.name.$model"  required/>
                        <div v-if="$v.aDClient.name.$anyDirty && $v.aDClient.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDClient.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDClient.code')" for="ad-client-code">Code</label>
                        <input type="text" class="form-control" name="code" id="ad-client-code"
                            :class="{'valid': !$v.aDClient.code.$invalid, 'invalid': $v.aDClient.code.$invalid }" v-model="$v.aDClient.code.$model"  required/>
                        <div v-if="$v.aDClient.code.$anyDirty && $v.aDClient.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDClient.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDClient.description')" for="ad-client-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-client-description"
                            :class="{'valid': !$v.aDClient.description.$invalid, 'invalid': $v.aDClient.description.$invalid }" v-model="$v.aDClient.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDClient.active')" for="ad-client-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-client-active"
                            :class="{'valid': !$v.aDClient.active.$invalid, 'invalid': $v.aDClient.active.$invalid }" v-model="$v.aDClient.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDClient.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-client-update.component.ts">
</script>
