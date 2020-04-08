<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="supportingDocument">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.supportingDocument.detail.title')">SupportingDocument</span> {{supportingDocument.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.supportingDocument.documentNo')">Document No</span>
                    </dt>
                    <dd>
                        <span>{{supportingDocument.documentNo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.supportingDocument.expirationDate')">Expiration Date</span>
                    </dt>
                    <dd>
                        <span>{{supportingDocument.expirationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.supportingDocument.file')">File</span>
                    </dt>
                    <dd>
                        <div v-if="supportingDocument.file">
                            <a v-on:click="openFile(supportingDocument.fileContentType, supportingDocument.file)" v-text="$t('entity.action.open')">open</a>
                            {{supportingDocument.fileContentType}}, {{byteSize(supportingDocument.file)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.supportingDocument.type')">Type</span>
                    </dt>
                    <dd>
                        <div v-if="supportingDocument.typeId">
                            <router-link :to="{name: 'DocumentTypeDetails', params: {documentTypeId: supportingDocument.typeId}}">{{supportingDocument.typeId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.supportingDocument.vendor')">Vendor</span>
                    </dt>
                    <dd>
                        <div v-if="supportingDocument.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: supportingDocument.vendorId}}">{{supportingDocument.vendorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="supportingDocument.id" :to="{name: 'DocumentTypeUpdate', params: {supportingDocumentId: supportingDocument.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./supporting-document-details.component.ts">
</script>
