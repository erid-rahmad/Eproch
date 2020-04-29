<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDReferenceList.home.title')" id="ad-reference-list-heading">AD Reference Lists</span>
            <router-link :to="{name: 'ADReferenceListCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-reference-list">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDReferenceList.home.createLabel')">
                    Create a new AD Reference List
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
        <div class="alert alert-warning" v-if="!isFetching && aDReferenceLists && aDReferenceLists.length === 0">
            <span v-text="$t('opusWebApp.aDReferenceList.home.notFound')">No aDReferenceLists found</span>
        </div>
        <div class="table-responsive" v-if="aDReferenceLists && aDReferenceLists.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDReferenceList.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('opusWebApp.aDReferenceList.value')">Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'value'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDReferenceList.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDReferenceList.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adReferenceId')"><span v-text="$t('opusWebApp.aDReferenceList.adReference')">Ad Reference</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adReferenceId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDReferenceList in aDReferenceLists"
                    :key="aDReferenceList.id">
                    <td>
                        <router-link :to="{name: 'ADReferenceListView', params: {aDReferenceListId: aDReferenceList.id}}">{{aDReferenceList.id}}</router-link>
                    </td>
                    <td>{{aDReferenceList.name}}</td>
                    <td>{{aDReferenceList.value}}</td>
                    <td>{{aDReferenceList.description}}</td>
                    <td>{{aDReferenceList.active}}</td>
                    <td>
                        <div v-if="aDReferenceList.adReferenceId">
                            <router-link :to="{name: 'ADReferenceView', params: {aDReferenceId: aDReferenceList.adReferenceId}}">{{aDReferenceList.adReferenceId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADReferenceListView', params: {aDReferenceListId: aDReferenceList.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADReferenceListEdit', params: {aDReferenceListId: aDReferenceList.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDReferenceList)"
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
            <span slot="modal-title"><span id="opusWebApp.aDReferenceList.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDReferenceList-heading" v-text="$t('opusWebApp.aDReferenceList.delete.question', {'id': removeId})">Are you sure you want to delete this AD Reference List?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDReferenceList" v-text="$t('entity.action.delete')" v-on:click="removeADReferenceList()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDReferenceLists && aDReferenceLists.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-reference-list.component.ts">
</script>
