<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.cLocation.home.title')" id="c-location-heading">C Locations</span>
            <router-link :to="{name: 'CLocationCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-c-location">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.cLocation.home.createLabel')">
                    Create a new C Location
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
        <div class="alert alert-warning" v-if="!isFetching && cLocations && cLocations.length === 0">
            <span v-text="$t('opusWebApp.cLocation.home.notFound')">No cLocations found</span>
        </div>
        <div class="table-responsive" v-if="cLocations && cLocations.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('streetAddress')"><span v-text="$t('opusWebApp.cLocation.streetAddress')">Street Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'streetAddress'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('postalCode')"><span v-text="$t('opusWebApp.cLocation.postalCode')">Postal Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('taxInvoiceAddress')"><span v-text="$t('opusWebApp.cLocation.taxInvoiceAddress')">Tax Invoice Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taxInvoiceAddress'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.cLocation.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('cityId')"><span v-text="$t('opusWebApp.cLocation.city')">City</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cityId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cLocation in cLocations"
                    :key="cLocation.id">
                    <td>
                        <router-link :to="{name: 'CLocationView', params: {cLocationId: cLocation.id}}">{{cLocation.id}}</router-link>
                    </td>
                    <td>{{cLocation.streetAddress}}</td>
                    <td>{{cLocation.postalCode}}</td>
                    <td>{{cLocation.taxInvoiceAddress}}</td>
                    <td>{{cLocation.active}}</td>
                    <td>
                        <div v-if="cLocation.cityId">
                            <router-link :to="{name: 'CCityView', params: {cCityId: cLocation.cityId}}">{{cLocation.cityId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CLocationView', params: {cLocationId: cLocation.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CLocationEdit', params: {cLocationId: cLocation.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cLocation)"
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
            <span slot="modal-title"><span id="opusWebApp.cLocation.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cLocation-heading" v-text="$t('opusWebApp.cLocation.delete.question', {'id': removeId})">Are you sure you want to delete this C Location?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cLocation" v-text="$t('entity.action.delete')" v-on:click="removeCLocation()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cLocations && cLocations.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-location.component.ts">
</script>
