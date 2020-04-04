<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.documentType.home.title')" id="document-type-heading">Document Types</span>
            <router-link :to="{name: 'DocumentTypeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-document-type">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.documentType.home.createLabel')">
                    Create a new Document Type
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
        <div class="alert alert-warning" v-if="!isFetching && documentTypes && documentTypes.length === 0">
            <span v-text="$t('opusWebApp.documentType.home.notFound')">No documentTypes found</span>
        </div>
        <div class="table-responsive" v-if="documentTypes && documentTypes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.documentType.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.documentType.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('hasExpirationDate')"><span v-text="$t('opusWebApp.documentType.hasExpirationDate')">Has Expiration Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasExpirationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mandatoryBusinessCategories')"><span v-text="$t('opusWebApp.documentType.mandatoryBusinessCategories')">Mandatory Business Categories</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mandatoryBusinessCategories'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('additionalBusinessCategories')"><span v-text="$t('opusWebApp.documentType.additionalBusinessCategories')">Additional Business Categories</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'additionalBusinessCategories'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mandatoryForCompany')"><span v-text="$t('opusWebApp.documentType.mandatoryForCompany')">Mandatory For Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mandatoryForCompany'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mandatoryForProfessional')"><span v-text="$t('opusWebApp.documentType.mandatoryForProfessional')">Mandatory For Professional</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mandatoryForProfessional'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('additionalForCompany')"><span v-text="$t('opusWebApp.documentType.additionalForCompany')">Additional For Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'additionalForCompany'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('additionalForProfessional')"><span v-text="$t('opusWebApp.documentType.additionalForProfessional')">Additional For Professional</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'additionalForProfessional'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.documentType.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="documentType in documentTypes"
                    :key="documentType.id">
                    <td>
                        <router-link :to="{name: 'DocumentTypeView', params: {documentTypeId: documentType.id}}">{{documentType.id}}</router-link>
                    </td>
                    <td>{{documentType.name}}</td>
                    <td>{{documentType.description}}</td>
                    <td>{{documentType.hasExpirationDate}}</td>
                    <td>{{documentType.mandatoryBusinessCategories}}</td>
                    <td>{{documentType.additionalBusinessCategories}}</td>
                    <td>{{documentType.mandatoryForCompany}}</td>
                    <td>{{documentType.mandatoryForProfessional}}</td>
                    <td>{{documentType.additionalForCompany}}</td>
                    <td>{{documentType.additionalForProfessional}}</td>
                    <td>{{documentType.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'DocumentTypeView', params: {documentTypeId: documentType.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'DocumentTypeEdit', params: {documentTypeId: documentType.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(documentType)"
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
            <span slot="modal-title"><span id="opusWebApp.documentType.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-documentType-heading" v-text="$t('opusWebApp.documentType.delete.question', {'id': removeId})">Are you sure you want to delete this Document Type?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-documentType" v-text="$t('entity.action.delete')" v-on:click="removeDocumentType()">Delete</button>
            </div>
        </b-modal>
        <div v-show="documentTypes && documentTypes.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./document-type.component.ts">
</script>
