<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.supportingDocument.home.createOrEditLabel" v-text="$t('opusWebApp.supportingDocument.home.createOrEditLabel')">Create or edit a SupportingDocument</h2>
                <div>
                    <div class="form-group" v-if="supportingDocument.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="supportingDocument.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.supportingDocument.documentNo')" for="supporting-document-documentNo">Document No</label>
                        <input type="text" class="form-control" name="documentNo" id="supporting-document-documentNo"
                            :class="{'valid': !$v.supportingDocument.documentNo.$invalid, 'invalid': $v.supportingDocument.documentNo.$invalid }" v-model="$v.supportingDocument.documentNo.$model"  required/>
                        <div v-if="$v.supportingDocument.documentNo.$anyDirty && $v.supportingDocument.documentNo.$invalid">
                            <small class="form-text text-danger" v-if="!$v.supportingDocument.documentNo.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.supportingDocument.expirationDate')" for="supporting-document-expirationDate">Expiration Date</label>
                        <div class="input-group">
                            <input id="supporting-document-expirationDate" type="date" class="form-control" name="expirationDate"  :class="{'valid': !$v.supportingDocument.expirationDate.$invalid, 'invalid': $v.supportingDocument.expirationDate.$invalid }"
                            v-model="$v.supportingDocument.expirationDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.supportingDocument.file')" for="supporting-document-file">File</label>
                        <div>
                            <div v-if="supportingDocument.file" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(supportingDocument.fileContentType, supportingDocument.file)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{supportingDocument.fileContentType}}, {{byteSize(supportingDocument.file)}}</span>
                                <button type="button" v-on:click="supportingDocument.file=null;supportingDocument.fileContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_file" id="file_file" v-on:change="setFileData($event, supportingDocument, 'file', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="file" id="supporting-document-file"
                            :class="{'valid': !$v.supportingDocument.file.$invalid, 'invalid': $v.supportingDocument.file.$invalid }" v-model="$v.supportingDocument.file.$model" />
                        <input type="hidden" class="form-control" name="fileContentType" id="supporting-document-fileContentType"
                            v-model="supportingDocument.fileContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.supportingDocument.type')" for="supporting-document-type">Type</label>
                        <select class="form-control" id="supporting-document-type" name="type" v-model="$v.supportingDocument.typeId.$model" required>
                            <option v-if="!supportingDocument.typeId" v-bind:value="null" selected></option>
                            <option v-bind:value="documentTypeOption.id" v-for="documentTypeOption in documentTypes" :key="documentTypeOption.id">{{documentTypeOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.supportingDocument.typeId.$anyDirty && $v.supportingDocument.typeId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.supportingDocument.typeId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.supportingDocument.vendor')" for="supporting-document-vendor">Vendor</label>
                        <select class="form-control" id="supporting-document-vendor" name="vendor" v-model="supportingDocument.vendorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="vendorOption.id" v-for="vendorOption in vendors" :key="vendorOption.id">{{vendorOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.supportingDocument.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./supporting-document-update.component.ts">
</script>
