<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.documentTypeBusinessCategory.home.createOrEditLabel" v-text="$t('opusWebApp.documentTypeBusinessCategory.home.createOrEditLabel')">Create or edit a DocumentTypeBusinessCategory</h2>
                <div>
                    <div class="form-group" v-if="documentTypeBusinessCategory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documentTypeBusinessCategory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentTypeBusinessCategory.mandatory')" for="document-type-business-category-mandatory">Mandatory</label>
                        <input type="checkbox" class="form-check" name="mandatory" id="document-type-business-category-mandatory"
                            :class="{'valid': !$v.documentTypeBusinessCategory.mandatory.$invalid, 'invalid': $v.documentTypeBusinessCategory.mandatory.$invalid }" v-model="$v.documentTypeBusinessCategory.mandatory.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentTypeBusinessCategory.additional')" for="document-type-business-category-additional">Additional</label>
                        <input type="checkbox" class="form-check" name="additional" id="document-type-business-category-additional"
                            :class="{'valid': !$v.documentTypeBusinessCategory.additional.$invalid, 'invalid': $v.documentTypeBusinessCategory.additional.$invalid }" v-model="$v.documentTypeBusinessCategory.additional.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentTypeBusinessCategory.documentType')" for="document-type-business-category-documentType">Document Type</label>
                        <select class="form-control" id="document-type-business-category-documentType" name="documentType" v-model="documentTypeBusinessCategory.documentType">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentTypeBusinessCategory.documentType && documentTypeOption.id === documentTypeBusinessCategory.documentType.id ? documentTypeBusinessCategory.documentType : documentTypeOption" v-for="documentTypeOption in documentTypes" :key="documentTypeOption.id">{{documentTypeOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.documentTypeBusinessCategory.businessCategory')" for="document-type-business-category-businessCategory">Business Category</label>
                        <select class="form-control" id="document-type-business-category-businessCategory" name="businessCategory" v-model="documentTypeBusinessCategory.businessCategory">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentTypeBusinessCategory.businessCategory && businessCategoryOption.id === documentTypeBusinessCategory.businessCategory.id ? documentTypeBusinessCategory.businessCategory : businessCategoryOption" v-for="businessCategoryOption in businessCategories" :key="businessCategoryOption.id">{{businessCategoryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documentTypeBusinessCategory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./document-type-business-category-update.component.ts">
</script>
