<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.documentType.home.createOrEditLabel" v-text="$t('opusWebApp.documentType.home.createOrEditLabel')">Create or edit a DocumentType</h2>
                <div>
                    <div class="form-group" v-if="documentType.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentType.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.name')" for="document-type-name">Name</label>
                        <input type="text" class="form-control" name="name" id="document-type-name"
                            :class="{'valid': !$v.documentType.name.$invalid, 'invalid': $v.documentType.name.$invalid }" v-model="$v.documentType.name.$model"  required/>
                        <div v-if="$v.documentType.name.$anyDirty && $v.documentType.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documentType.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.description')" for="document-type-description">Description</label>
                        <input type="text" class="form-control" name="description" id="document-type-description"
                            :class="{'valid': !$v.documentType.description.$invalid, 'invalid': $v.documentType.description.$invalid }" v-model="$v.documentType.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.hasExpirationDate')" for="document-type-hasExpirationDate">Has Expiration Date</label>
                        <input type="checkbox" class="form-check" name="hasExpirationDate" id="document-type-hasExpirationDate"
                            :class="{'valid': !$v.documentType.hasExpirationDate.$invalid, 'invalid': $v.documentType.hasExpirationDate.$invalid }" v-model="$v.documentType.hasExpirationDate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.forCompany')" for="document-type-forCompany">For Company</label>
                        <input type="checkbox" class="form-check" name="forCompany" id="document-type-forCompany"
                            :class="{'valid': !$v.documentType.forCompany.$invalid, 'invalid': $v.documentType.forCompany.$invalid }" v-model="$v.documentType.forCompany.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.forProfessional')" for="document-type-forProfessional">For Professional</label>
                        <input type="checkbox" class="form-check" name="forProfessional" id="document-type-forProfessional"
                            :class="{'valid': !$v.documentType.forProfessional.$invalid, 'invalid': $v.documentType.forProfessional.$invalid }" v-model="$v.documentType.forProfessional.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentType.active')" for="document-type-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="document-type-active"
                            :class="{'valid': !$v.documentType.active.$invalid, 'invalid': $v.documentType.active.$invalid }" v-model="$v.documentType.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentType.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./document-type-update.component.ts">
</script>
