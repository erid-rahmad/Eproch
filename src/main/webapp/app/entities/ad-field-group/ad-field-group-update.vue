<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDFieldGroup.home.createOrEditLabel" v-text="$t('opusWebApp.aDFieldGroup.home.createOrEditLabel')">Create or edit a ADFieldGroup</h2>
                <div>
                    <div class="form-group" v-if="aDFieldGroup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDFieldGroup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDFieldGroup.name')" for="ad-field-group-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-field-group-name"
                            :class="{'valid': !$v.aDFieldGroup.name.$invalid, 'invalid': $v.aDFieldGroup.name.$invalid }" v-model="$v.aDFieldGroup.name.$model"  required/>
                        <div v-if="$v.aDFieldGroup.name.$anyDirty && $v.aDFieldGroup.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDFieldGroup.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDFieldGroup.collapsible')" for="ad-field-group-collapsible">Collapsible</label>
                        <input type="checkbox" class="form-check" name="collapsible" id="ad-field-group-collapsible"
                            :class="{'valid': !$v.aDFieldGroup.collapsible.$invalid, 'invalid': $v.aDFieldGroup.collapsible.$invalid }" v-model="$v.aDFieldGroup.collapsible.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDFieldGroup.collapseByDefault')" for="ad-field-group-collapseByDefault">Collapse By Default</label>
                        <input type="checkbox" class="form-check" name="collapseByDefault" id="ad-field-group-collapseByDefault"
                            :class="{'valid': !$v.aDFieldGroup.collapseByDefault.$invalid, 'invalid': $v.aDFieldGroup.collapseByDefault.$invalid }" v-model="$v.aDFieldGroup.collapseByDefault.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDFieldGroup.active')" for="ad-field-group-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-field-group-active"
                            :class="{'valid': !$v.aDFieldGroup.active.$invalid, 'invalid': $v.aDFieldGroup.active.$invalid }" v-model="$v.aDFieldGroup.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDFieldGroup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-field-group-update.component.ts">
</script>
