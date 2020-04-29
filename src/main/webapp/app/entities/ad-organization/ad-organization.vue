<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDOrganization.home.title')" id="ad-organization-heading">AD Organizations</span>
            <router-link :to="{name: 'ADOrganizationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-organization">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDOrganization.home.createLabel')">
                    Create a new AD Organization
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
        <div class="alert alert-warning" v-if="!isFetching && aDOrganizations && aDOrganizations.length === 0">
            <span v-text="$t('opusWebApp.aDOrganization.home.notFound')">No aDOrganizations found</span>
        </div>
        <div class="table-responsive" v-if="aDOrganizations && aDOrganizations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDOrganization.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('opusWebApp.aDOrganization.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDOrganization.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDOrganization.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adClientId')"><span v-text="$t('opusWebApp.aDOrganization.adClient')">Ad Client</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adClientId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDOrganization in aDOrganizations"
                    :key="aDOrganization.id">
                    <td>
                        <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDOrganization.id}}">{{aDOrganization.id}}</router-link>
                    </td>
                    <td>{{aDOrganization.name}}</td>
                    <td>{{aDOrganization.code}}</td>
                    <td>{{aDOrganization.description}}</td>
                    <td>{{aDOrganization.active}}</td>
                    <td>
                        <div v-if="aDOrganization.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDOrganization.adClientId}}">{{aDOrganization.adClientId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDOrganization.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADOrganizationEdit', params: {aDOrganizationId: aDOrganization.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDOrganization)"
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
            <span slot="modal-title"><span id="opusWebApp.aDOrganization.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDOrganization-heading" v-text="$t('opusWebApp.aDOrganization.delete.question', {'id': removeId})">Are you sure you want to delete this AD Organization?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDOrganization" v-text="$t('entity.action.delete')" v-on:click="removeADOrganization()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDOrganizations && aDOrganizations.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-organization.component.ts">
</script>
