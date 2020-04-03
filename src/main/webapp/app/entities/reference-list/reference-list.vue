<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.referenceList.home.title')" id="reference-list-heading">Reference Lists</span>
            <router-link :to="{name: 'ReferenceListCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-reference-list">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.referenceList.home.createLabel')">
                    Create a new Reference List
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
        <div class="alert alert-warning" v-if="!isFetching && referenceLists && referenceLists.length === 0">
            <span v-text="$t('opusWebApp.referenceList.home.notFound')">No referenceLists found</span>
        </div>
        <div class="table-responsive" v-if="referenceLists && referenceLists.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.referenceList.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.referenceList.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('opusWebApp.referenceList.value')">Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'value'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('referenceId')"><span v-text="$t('opusWebApp.referenceList.reference')">Reference</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'referenceId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="referenceList in referenceLists"
                    :key="referenceList.id">
                    <td>
                        <router-link :to="{name: 'ReferenceListView', params: {referenceListId: referenceList.id}}">{{referenceList.id}}</router-link>
                    </td>
                    <td>{{referenceList.name}}</td>
                    <td>{{referenceList.description}}</td>
                    <td>{{referenceList.value}}</td>
                    <td>
                        <div v-if="referenceList.referenceId">
                            <router-link :to="{name: 'ReferenceView', params: {referenceId: referenceList.referenceId}}">{{referenceList.referenceId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ReferenceListView', params: {referenceListId: referenceList.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ReferenceListEdit', params: {referenceListId: referenceList.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(referenceList)"
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
            <span slot="modal-title"><span id="opusWebApp.referenceList.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-referenceList-heading" v-text="$t('opusWebApp.referenceList.delete.question', {'id': removeId})">Are you sure you want to delete this Reference List?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-referenceList" v-text="$t('entity.action.delete')" v-on:click="removeReferenceList()">Delete</button>
            </div>
        </b-modal>
        <div v-show="referenceLists && referenceLists.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./reference-list.component.ts">
</script>
