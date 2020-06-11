<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDTable.home.title')" id="ad-table-heading">AD Tables</span>
            <router-link :to="{name: 'ADTableCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-table">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDTable.home.createLabel')">
                    Create a new AD Table
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
        <div class="alert alert-warning" v-if="!isFetching && aDTables && aDTables.length === 0">
            <span v-text="$t('opusWebApp.aDTable.home.notFound')">No aDTables found</span>
        </div>
        <div class="table-responsive" v-if="aDTables && aDTables.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDTable.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('view')"><span v-text="$t('opusWebApp.aDTable.view')">View</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'view'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDTable.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adOrganizationId')"><span v-text="$t('opusWebApp.aDTable.adOrganization')">Ad Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adOrganizationId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDTable in aDTables"
                    :key="aDTable.id">
                    <td>
                        <router-link :to="{name: 'ADTableView', params: {aDTableId: aDTable.id}}">{{aDTable.id}}</router-link>
                    </td>
                    <td>{{aDTable.name}}</td>
                    <td>{{aDTable.view}}</td>
                    <td>{{aDTable.active}}</td>
                    <td>
                        <div v-if="aDTable.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDTable.adOrganizationId}}">{{aDTable.adOrganizationId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADTableView', params: {aDTableId: aDTable.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADTableEdit', params: {aDTableId: aDTable.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDTable)"
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
            <span slot="modal-title"><span id="opusWebApp.aDTable.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDTable-heading" v-text="$t('opusWebApp.aDTable.delete.question', {'id': removeId})">Are you sure you want to delete this AD Table?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDTable" v-text="$t('entity.action.delete')" v-on:click="removeADTable()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDTables && aDTables.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-table.component.ts">
</script>
