<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDField.home.title')" id="ad-field-heading">AD Fields</span>
            <router-link :to="{name: 'ADFieldCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-field">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDField.home.createLabel')">
                    Create a new AD Field
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
        <div class="alert alert-warning" v-if="!isFetching && aDFields && aDFields.length === 0">
            <span v-text="$t('opusWebApp.aDField.home.notFound')">No aDFields found</span>
        </div>
        <div class="table-responsive" v-if="aDFields && aDFields.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDField.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDField.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('hint')"><span v-text="$t('opusWebApp.aDField.hint')">Hint</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hint'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('staticText')"><span v-text="$t('opusWebApp.aDField.staticText')">Static Text</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'staticText'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('staticField')"><span v-text="$t('opusWebApp.aDField.staticField')">Static Field</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'staticField'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('labelOnly')"><span v-text="$t('opusWebApp.aDField.labelOnly')">Label Only</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'labelOnly'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('showLabel')"><span v-text="$t('opusWebApp.aDField.showLabel')">Show Label</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'showLabel'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('showInGrid')"><span v-text="$t('opusWebApp.aDField.showInGrid')">Show In Grid</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'showInGrid'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('showInDetail')"><span v-text="$t('opusWebApp.aDField.showInDetail')">Show In Detail</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'showInDetail'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('gridSequence')"><span v-text="$t('opusWebApp.aDField.gridSequence')">Grid Sequence</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gridSequence'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('detailSequence')"><span v-text="$t('opusWebApp.aDField.detailSequence')">Detail Sequence</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'detailSequence'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('displayLogic')"><span v-text="$t('opusWebApp.aDField.displayLogic')">Display Logic</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'displayLogic'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('writable')"><span v-text="$t('opusWebApp.aDField.writable')">Writable</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writable'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('columnNo')"><span v-text="$t('opusWebApp.aDField.columnNo')">Column No</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnNo'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('columnSpan')"><span v-text="$t('opusWebApp.aDField.columnSpan')">Column Span</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnSpan'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDField.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adClientId')"><span v-text="$t('opusWebApp.aDField.adClient')">Ad Client</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adClientId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adOrganizationId')"><span v-text="$t('opusWebApp.aDField.adOrganization')">Ad Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adOrganizationId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDField in aDFields"
                    :key="aDField.id">
                    <td>
                        <router-link :to="{name: 'ADFieldView', params: {aDFieldId: aDField.id}}">{{aDField.id}}</router-link>
                    </td>
                    <td>{{aDField.name}}</td>
                    <td>{{aDField.description}}</td>
                    <td>{{aDField.hint}}</td>
                    <td>{{aDField.staticText}}</td>
                    <td>{{aDField.staticField}}</td>
                    <td>{{aDField.labelOnly}}</td>
                    <td>{{aDField.showLabel}}</td>
                    <td>{{aDField.showInGrid}}</td>
                    <td>{{aDField.showInDetail}}</td>
                    <td>{{aDField.gridSequence}}</td>
                    <td>{{aDField.detailSequence}}</td>
                    <td>{{aDField.displayLogic}}</td>
                    <td>{{aDField.writable}}</td>
                    <td>{{aDField.columnNo}}</td>
                    <td>{{aDField.columnSpan}}</td>
                    <td>{{aDField.active}}</td>
                    <td>
                        <div v-if="aDField.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDField.adClientId}}">{{aDField.adClientId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDField.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDField.adOrganizationId}}">{{aDField.adOrganizationId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADFieldView', params: {aDFieldId: aDField.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADFieldEdit', params: {aDFieldId: aDField.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDField)"
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
            <span slot="modal-title"><span id="opusWebApp.aDField.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDField-heading" v-text="$t('opusWebApp.aDField.delete.question', {'id': removeId})">Are you sure you want to delete this AD Field?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDField" v-text="$t('entity.action.delete')" v-on:click="removeADField()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDFields && aDFields.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-field.component.ts">
</script>
