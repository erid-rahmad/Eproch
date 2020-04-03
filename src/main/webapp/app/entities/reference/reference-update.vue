<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.reference.home.createOrEditLabel" v-text="$t('opusWebApp.reference.home.createOrEditLabel')">Create or edit a Reference</h2>
                <div>
                    <div class="form-group" v-if="reference.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="reference.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.reference.name')" for="reference-name">Name</label>
                        <input type="text" class="form-control" name="name" id="reference-name"
                            :class="{'valid': !$v.reference.name.$invalid, 'invalid': $v.reference.name.$invalid }" v-model="$v.reference.name.$model"  required/>
                        <div v-if="$v.reference.name.$anyDirty && $v.reference.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.reference.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.reference.description')" for="reference-description">Description</label>
                        <input type="text" class="form-control" name="description" id="reference-description"
                            :class="{'valid': !$v.reference.description.$invalid, 'invalid': $v.reference.description.$invalid }" v-model="$v.reference.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.reference.referenceType')" for="reference-referenceType">Reference Type</label>
                        <select class="form-control" name="referenceType" :class="{'valid': !$v.reference.referenceType.$invalid, 'invalid': $v.reference.referenceType.$invalid }" v-model="$v.reference.referenceType.$model" id="reference-referenceType" >
                            <option value="LIST" v-bind:label="$t('opusWebApp.ReferenceType.LIST')">LIST</option>
                            <option value="TABLE" v-bind:label="$t('opusWebApp.ReferenceType.TABLE')">TABLE</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.reference.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./reference-update.component.ts">
</script>
