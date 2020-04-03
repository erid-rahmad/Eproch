<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documentTypeBusinessCategory">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.documentTypeBusinessCategory.detail.title')">DocumentTypeBusinessCategory</span> {{documentTypeBusinessCategory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.documentTypeBusinessCategory.mandatory')">Mandatory</span>
                    </dt>
                    <dd>
                        <span>{{documentTypeBusinessCategory.mandatory}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentTypeBusinessCategory.additional')">Additional</span>
                    </dt>
                    <dd>
                        <span>{{documentTypeBusinessCategory.additional}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentTypeBusinessCategory.documentType')">Document Type</span>
                    </dt>
                    <dd>
                        <div v-if="documentTypeBusinessCategory.documentType">
                            <router-link :to="{name: 'DocumentTypeView', params: {documentTypeId: documentTypeBusinessCategory.documentType.id}}">{{documentTypeBusinessCategory.documentType.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.documentTypeBusinessCategory.businessCategory')">Business Category</span>
                    </dt>
                    <dd>
                        <div v-if="documentTypeBusinessCategory.businessCategory">
                            <router-link :to="{name: 'BusinessCategoryView', params: {businessCategoryId: documentTypeBusinessCategory.businessCategory.id}}">{{documentTypeBusinessCategory.businessCategory.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documentTypeBusinessCategory.id" :to="{name: 'DocumentTypeBusinessCategoryEdit', params: {documentTypeBusinessCategoryId: documentTypeBusinessCategory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-type-business-category-details.component.ts">
</script>
