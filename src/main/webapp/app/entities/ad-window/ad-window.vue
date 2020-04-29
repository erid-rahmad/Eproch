<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDWindow.home.title')" id="ad-window-heading">AD Windows</span>
            <router-link :to="{name: 'ADWindowCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-window">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDWindow.home.createLabel')">
                    Create a new AD Window
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
        <div class="alert alert-warning" v-if="!isFetching && aDWindows && aDWindows.length === 0">
            <span v-text="$t('opusWebApp.aDWindow.home.notFound')">No aDWindows found</span>
        </div>
        <div class="table-responsive" v-if="aDWindows && aDWindows.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDWindow.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDWindow.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type')"><span v-text="$t('opusWebApp.aDWindow.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDWindow.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adClientId')"><span v-text="$t('opusWebApp.aDWindow.adClient')">Ad Client</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adClientId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adOrganizationId')"><span v-text="$t('opusWebApp.aDWindow.adOrganization')">Ad Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adOrganizationId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDWindow in aDWindows"
                    :key="aDWindow.id">
                    <td>
                        <router-link :to="{name: 'ADWindowView', params: {aDWindowId: aDWindow.id}}">{{aDWindow.id}}</router-link>
                    </td>
                    <td>{{aDWindow.name}}</td>
                    <td>{{aDWindow.description}}</td>
                    <td v-text="$t('opusWebApp.ADWindowType.' + aDWindow.type)">{{aDWindow.type}}</td>
                    <td>{{aDWindow.active}}</td>
                    <td>
                        <div v-if="aDWindow.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDWindow.adClientId}}">{{aDWindow.adClientId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDWindow.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDWindow.adOrganizationId}}">{{aDWindow.adOrganizationId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADWindowView', params: {aDWindowId: aDWindow.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADWindowEdit', params: {aDWindowId: aDWindow.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDWindow)"
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
            <span slot="modal-title"><span id="opusWebApp.aDWindow.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDWindow-heading" v-text="$t('opusWebApp.aDWindow.delete.question', {'id': removeId})">Are you sure you want to delete this AD Window?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDWindow" v-text="$t('entity.action.delete')" v-on:click="removeADWindow()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDWindows && aDWindows.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-window.component.ts">
</script>
