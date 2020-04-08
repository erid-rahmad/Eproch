<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.documentTypeBusinessCategory.home.title')" id="document-type-business-category-heading">Document Type Business Categories</span>
            <router-link :to="{name: 'DocumentTypeBusinessCategoryNew'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-document-type-business-category">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.documentTypeBusinessCategory.home.createLabel')">
                    Create a new Document Type Business Category
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && documentTypeBusinessCategories && documentTypeBusinessCategories.length === 0">
            <span v-text="$t('opusWebApp.documentTypeBusinessCategory.home.notFound')">No documentTypeBusinessCategories found</span>
        </div>
        <div class="table-responsive" v-if="documentTypeBusinessCategories && documentTypeBusinessCategories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('opusWebApp.documentTypeBusinessCategory.mandatory')">Mandatory</span></th>
                    <th><span v-text="$t('opusWebApp.documentTypeBusinessCategory.additional')">Additional</span></th>
                    <th><span v-text="$t('opusWebApp.documentTypeBusinessCategory.documentType')">Document Type</span></th>
                    <th><span v-text="$t('opusWebApp.documentTypeBusinessCategory.businessCategory')">Business Category</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentTypeBusinessCategory in documentTypeBusinessCategories"
                    :key="documentTypeBusinessCategory.id">
                    <td>
                        <router-link :to="{name: 'DocumentTypeBusinessCategoryDetails', params: {documentTypeBusinessCategoryId: documentTypeBusinessCategory.id}}">{{documentTypeBusinessCategory.id}}</router-link>
                    </td>
                    <td>{{documentTypeBusinessCategory.mandatory}}</td>
                    <td>{{documentTypeBusinessCategory.additional}}</td>
                    <td>
                        <div v-if="documentTypeBusinessCategory.documentType">
                            <router-link :to="{name: 'DocumentTypeDetails', params: {documentTypeId: documentTypeBusinessCategory.documentType.id}}">{{documentTypeBusinessCategory.documentType.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="documentTypeBusinessCategory.businessCategory">
                            <router-link :to="{name: 'BusinessCategoryDetails', params: {businessCategoryId: documentTypeBusinessCategory.businessCategory.id}}">{{documentTypeBusinessCategory.businessCategory.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentTypeBusinessCategoryDetails', params: {documentTypeBusinessCategoryId: documentTypeBusinessCategory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentTypeBusinessCategoryUpdate', params: {documentTypeBusinessCategoryId: documentTypeBusinessCategory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentTypeBusinessCategory)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="opusWebApp.documentTypeBusinessCategory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentTypeBusinessCategory-heading" v-text="$t('opusWebApp.documentTypeBusinessCategory.delete.question', {'id': removeId})">Are you sure you want to delete this Document Type Business Category?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentTypeBusinessCategory" v-text="$t('entity.action.delete')" v-on:click="removeDocumentTypeBusinessCategory()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./document-type-business-category.component.ts">
</script>
