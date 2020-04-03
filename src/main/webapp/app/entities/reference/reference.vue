<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.reference.home.title')" id="reference-heading">References</span>
            <router-link :to="{name: 'ReferenceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-reference">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.reference.home.createLabel')">
                    Create a new Reference
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
        <div class="alert alert-warning" v-if="!isFetching && references && references.length === 0">
            <span v-text="$t('opusWebApp.reference.home.notFound')">No references found</span>
        </div>
        <div class="table-responsive" v-if="references && references.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.reference.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.reference.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('referenceType')"><span v-text="$t('opusWebApp.reference.referenceType')">Reference Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'referenceType'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="reference in references"
                    :key="reference.id">
                    <td>
                        <router-link :to="{name: 'ReferenceView', params: {referenceId: reference.id}}">{{reference.id}}</router-link>
                    </td>
                    <td>{{reference.name}}</td>
                    <td>{{reference.description}}</td>
                    <td v-text="$t('opusWebApp.ReferenceType.' + reference.referenceType)">{{reference.referenceType}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ReferenceView', params: {referenceId: reference.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ReferenceEdit', params: {referenceId: reference.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(reference)"
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
            <span slot="modal-title"><span id="opusWebApp.reference.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-reference-heading" v-text="$t('opusWebApp.reference.delete.question', {'id': removeId})">Are you sure you want to delete this Reference?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-reference" v-text="$t('entity.action.delete')" v-on:click="removeReference()">Delete</button>
            </div>
        </b-modal>
        <div v-show="references && references.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./reference.component.ts">
</script>
