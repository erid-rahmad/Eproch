<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.aDTab.home.title')" id="ad-tab-heading">AD Tabs</span>
            <router-link :to="{name: 'ADTabCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ad-tab">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.aDTab.home.createLabel')">
                    Create a new AD Tab
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
        <div class="alert alert-warning" v-if="!isFetching && aDTabs && aDTabs.length === 0">
            <span v-text="$t('opusWebApp.aDTab.home.notFound')">No aDTabs found</span>
        </div>
        <div class="table-responsive" v-if="aDTabs && aDTabs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.aDTab.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.aDTab.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('iconName')"><span v-text="$t('opusWebApp.aDTab.iconName')">Icon Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'iconName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('targetEndpoint')"><span v-text="$t('opusWebApp.aDTab.targetEndpoint')">Target Endpoint</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'targetEndpoint'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('writable')"><span v-text="$t('opusWebApp.aDTab.writable')">Writable</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'writable'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('displayLogic')"><span v-text="$t('opusWebApp.aDTab.displayLogic')">Display Logic</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'displayLogic'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('readOnlyLogic')"><span v-text="$t('opusWebApp.aDTab.readOnlyLogic')">Read Only Logic</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readOnlyLogic'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('filterQuery')"><span v-text="$t('opusWebApp.aDTab.filterQuery')">Filter Query</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'filterQuery'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('orderQuery')"><span v-text="$t('opusWebApp.aDTab.orderQuery')">Order Query</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'orderQuery'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('tabSequence')"><span v-text="$t('opusWebApp.aDTab.tabSequence')">Tab Sequence</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tabSequence'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.aDTab.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adOrganizationId')"><span v-text="$t('opusWebApp.aDTab.adOrganization')">Ad Organization</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adOrganizationId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adTableId')"><span v-text="$t('opusWebApp.aDTab.adTable')">Ad Table</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adTableId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('parentColumnId')"><span v-text="$t('opusWebApp.aDTab.parentColumn')">Parent Column</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentColumnId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('foreignColumnId')"><span v-text="$t('opusWebApp.aDTab.foreignColumn')">Foreign Column</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'foreignColumnId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('adWindowId')"><span v-text="$t('opusWebApp.aDTab.adWindow')">Ad Window</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adWindowId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('parentTabId')"><span v-text="$t('opusWebApp.aDTab.parentTab')">Parent Tab</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentTabId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="aDTab in aDTabs"
                    :key="aDTab.id">
                    <td>
                        <router-link :to="{name: 'ADTabView', params: {aDTabId: aDTab.id}}">{{aDTab.id}}</router-link>
                    </td>
                    <td>{{aDTab.name}}</td>
                    <td>{{aDTab.description}}</td>
                    <td>{{aDTab.iconName}}</td>
                    <td>{{aDTab.targetEndpoint}}</td>
                    <td>{{aDTab.writable}}</td>
                    <td>{{aDTab.displayLogic}}</td>
                    <td>{{aDTab.readOnlyLogic}}</td>
                    <td>{{aDTab.filterQuery}}</td>
                    <td>{{aDTab.orderQuery}}</td>
                    <td>{{aDTab.tabSequence}}</td>
                    <td>{{aDTab.active}}</td>
                    <td>
                        <div v-if="aDTab.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDTab.adOrganizationId}}">{{aDTab.adOrganizationId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDTab.adTableId">
                            <router-link :to="{name: 'ADTableView', params: {aDTableId: aDTab.adTableId}}">{{aDTab.adTableId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDTab.parentColumnId">
                            <router-link :to="{name: 'ADColumnView', params: {aDColumnId: aDTab.parentColumnId}}">{{aDTab.parentColumnId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDTab.foreignColumnId">
                            <router-link :to="{name: 'ADColumnView', params: {aDColumnId: aDTab.foreignColumnId}}">{{aDTab.foreignColumnId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDTab.adWindowId">
                            <router-link :to="{name: 'ADWindowView', params: {aDWindowId: aDTab.adWindowId}}">{{aDTab.adWindowId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="aDTab.parentTabId">
                            <router-link :to="{name: 'ADTabView', params: {aDTabId: aDTab.parentTabId}}">{{aDTab.parentTabId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ADTabView', params: {aDTabId: aDTab.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ADTabEdit', params: {aDTabId: aDTab.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(aDTab)"
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
            <span slot="modal-title"><span id="opusWebApp.aDTab.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-aDTab-heading" v-text="$t('opusWebApp.aDTab.delete.question', {'id': removeId})">Are you sure you want to delete this AD Tab?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-aDTab" v-text="$t('entity.action.delete')" v-on:click="removeADTab()">Delete</button>
            </div>
        </b-modal>
        <div v-show="aDTabs && aDTabs.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-tab.component.ts">
</script>
