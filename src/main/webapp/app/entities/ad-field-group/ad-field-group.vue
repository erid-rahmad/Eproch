<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDFieldGroup.home.title')" id="ad-field-group-heading">AD Field Groups</span>
            <router-link :to="{name: 'ADFieldGroupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-field-group">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDFieldGroup.home.createLabel')">
                    Create a new AD Field Group
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
        <div class="alert alert-warning" v-if="!isFetching && aDFieldGroups && aDFieldGroups.length === 0">
            <span v-text="$t('opusWebApp.aDFieldGroup.home.notFound')">No aDFieldGroups found</span>
        </div>
        <div class="table-responsive" v-if="aDFieldGroups && aDFieldGroups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDFieldGroup.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('collapsible')"><span v-text="$t('opusWebApp.aDFieldGroup.collapsible')">Collapsible</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'collapsible'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('collapseByDefault')"><span v-text="$t('opusWebApp.aDFieldGroup.collapseByDefault')">Collapse By Default</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'collapseByDefault'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDFieldGroup.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDFieldGroup in aDFieldGroups"
                    :key="aDFieldGroup.id">
                    <td>
                        <router-link :to="{name: 'ADFieldGroupView', params: {aDFieldGroupId: aDFieldGroup.id}}">{{aDFieldGroup.id}}</router-link>
                    </td>
                    <td>{{aDFieldGroup.name}}</td>
                    <td>{{aDFieldGroup.collapsible}}</td>
                    <td>{{aDFieldGroup.collapseByDefault}}</td>
                    <td>{{aDFieldGroup.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADFieldGroupView', params: {aDFieldGroupId: aDFieldGroup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADFieldGroupEdit', params: {aDFieldGroupId: aDFieldGroup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDFieldGroup)"
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
            <span slot="modal-title"><span id="opusWebApp.aDFieldGroup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDFieldGroup-heading" v-text="$t('opusWebApp.aDFieldGroup.delete.question', {'id': removeId})">Are you sure you want to delete this AD Field Group?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDFieldGroup" v-text="$t('entity.action.delete')" v-on:click="removeADFieldGroup()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDFieldGroups && aDFieldGroups.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-field-group.component.ts">
</script>
