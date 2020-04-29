<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDReference.home.createOrEditLabel" v-text="$t('opusWebApp.aDReference.home.createOrEditLabel')">Create or edit a ADReference</h2>
                <div>
                    <div class="form-group" v-if="aDReference.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDReference.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReference.name')" for="ad-reference-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-reference-name"
                            :class="{'valid': !$v.aDReference.name.$invalid, 'invalid': $v.aDReference.name.$invalid }" v-model="$v.aDReference.name.$model"  required/>
                        <div v-if="$v.aDReference.name.$anyDirty && $v.aDReference.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDReference.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReference.value')" for="ad-reference-value">Value</label>
                        <input type="text" class="form-control" name="value" id="ad-reference-value"
                            :class="{'valid': !$v.aDReference.value.$invalid, 'invalid': $v.aDReference.value.$invalid }" v-model="$v.aDReference.value.$model"  required/>
                        <div v-if="$v.aDReference.value.$anyDirty && $v.aDReference.value.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDReference.value.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReference.description')" for="ad-reference-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-reference-description"
                            :class="{'valid': !$v.aDReference.description.$invalid, 'invalid': $v.aDReference.description.$invalid }" v-model="$v.aDReference.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReference.referenceType')" for="ad-reference-referenceType">Reference Type</label>
                        <select class="form-control" name="referenceType" :class="{'valid': !$v.aDReference.referenceType.$invalid, 'invalid': $v.aDReference.referenceType.$invalid }" v-model="$v.aDReference.referenceType.$model" id="ad-reference-referenceType" >
                            <option value="LIST" v-bind:label="$t('opusWebApp.ADReferenceType.LIST')">LIST</option>
                            <option value="TABLE" v-bind:label="$t('opusWebApp.ADReferenceType.TABLE')">TABLE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDReference.active')" for="ad-reference-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-reference-active"
                            :class="{'valid': !$v.aDReference.active.$invalid, 'invalid': $v.aDReference.active.$invalid }" v-model="$v.aDReference.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDReference.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-reference-update.component.ts">
</script>
