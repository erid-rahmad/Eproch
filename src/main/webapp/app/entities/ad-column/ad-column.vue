<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDColumn.home.title')" id="ad-column-heading">AD Columns</span>
            <router-link :to="{name: 'ADColumnCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-column">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDColumn.home.createLabel')">
                    Create a new AD Column
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
        <div class="alert alert-warning" v-if="!isFetching && aDColumns && aDColumns.length === 0">
            <span v-text="$t('opusWebApp.aDColumn.home.notFound')">No aDColumns found</span>
        </div>
        <div class="table-responsive" v-if="aDColumns && aDColumns.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDColumn.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sqlName')"><span v-text="$t('opusWebApp.aDColumn.sqlName')">Sql Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sqlName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDColumn.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fieldLength')"><span v-text="$t('opusWebApp.aDColumn.fieldLength')">Field Length</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldLength'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('key')"><span v-text="$t('opusWebApp.aDColumn.key')">Key</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'key'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type')"><span v-text="$t('opusWebApp.aDColumn.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mandatory')"><span v-text="$t('opusWebApp.aDColumn.mandatory')">Mandatory</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mandatory'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mandatoryLogic')"><span v-text="$t('opusWebApp.aDColumn.mandatoryLogic')">Mandatory Logic</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mandatoryLogic'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('readOnlyLogic')"><span v-text="$t('opusWebApp.aDColumn.readOnlyLogic')">Read Only Logic</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readOnlyLogic'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('updatable')"><span v-text="$t('opusWebApp.aDColumn.updatable')">Updatable</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updatable'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('defaultValue')"><span v-text="$t('opusWebApp.aDColumn.defaultValue')">Default Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'defaultValue'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('formatPattern')"><span v-text="$t('opusWebApp.aDColumn.formatPattern')">Format Pattern</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'formatPattern'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('minLength')"><span v-text="$t('opusWebApp.aDColumn.minLength')">Min Length</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'minLength'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('maxLength')"><span v-text="$t('opusWebApp.aDColumn.maxLength')">Max Length</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'maxLength'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('minValue')"><span v-text="$t('opusWebApp.aDColumn.minValue')">Min Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'minValue'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('maxValue')"><span v-text="$t('opusWebApp.aDColumn.maxValue')">Max Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'maxValue'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDColumn.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adClientId')"><span v-text="$t('opusWebApp.aDColumn.adClient')">Ad Client</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adClientId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adOrganizationId')"><span v-text="$t('opusWebApp.aDColumn.adOrganization')">Ad Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adOrganizationId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adReferenceId')"><span v-text="$t('opusWebApp.aDColumn.adReference')">Ad Reference</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adReferenceId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adTableId')"><span v-text="$t('opusWebApp.aDColumn.adTable')">Ad Table</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adTableId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDColumn in aDColumns"
                    :key="aDColumn.id">
                    <td>
                        <router-link :to="{name: 'ADColumnView', params: {aDColumnId: aDColumn.id}}">{{aDColumn.id}}</router-link>
                    </td>
                    <td>{{aDColumn.name}}</td>
                    <td>{{aDColumn.sqlName}}</td>
                    <td>{{aDColumn.description}}</td>
                    <td>{{aDColumn.fieldLength}}</td>
                    <td>{{aDColumn.key}}</td>
                    <td v-text="$t('opusWebApp.ADColumnType.' + aDColumn.type)">{{aDColumn.type}}</td>
                    <td>{{aDColumn.mandatory}}</td>
                    <td>{{aDColumn.mandatoryLogic}}</td>
                    <td>{{aDColumn.readOnlyLogic}}</td>
                    <td>{{aDColumn.updatable}}</td>
                    <td>{{aDColumn.defaultValue}}</td>
                    <td>{{aDColumn.formatPattern}}</td>
                    <td>{{aDColumn.minLength}}</td>
                    <td>{{aDColumn.maxLength}}</td>
                    <td>{{aDColumn.minValue}}</td>
                    <td>{{aDColumn.maxValue}}</td>
                    <td>{{aDColumn.active}}</td>
                    <td>
                        <div v-if="aDColumn.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDColumn.adClientId}}">{{aDColumn.adClientId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDColumn.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDColumn.adOrganizationId}}">{{aDColumn.adOrganizationId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDColumn.adReferenceId">
                            <router-link :to="{name: 'ADReferenceView', params: {aDReferenceId: aDColumn.adReferenceId}}">{{aDColumn.adReferenceId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDColumn.adTableId">
                            <router-link :to="{name: 'ADTableView', params: {aDTableId: aDColumn.adTableId}}">{{aDColumn.adTableId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADColumnView', params: {aDColumnId: aDColumn.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADColumnEdit', params: {aDColumnId: aDColumn.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDColumn)"
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
            <span slot="modal-title"><span id="opusWebApp.aDColumn.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDColumn-heading" v-text="$t('opusWebApp.aDColumn.delete.question', {'id': removeId})">Are you sure you want to delete this AD Column?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDColumn" v-text="$t('entity.action.delete')" v-on:click="removeADColumn()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDColumns && aDColumns.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-column.component.ts">
</script>
