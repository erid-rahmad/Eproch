<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.cCity.home.title')" id="c-city-heading">C Cities</span>
            <router-link :to="{name: 'CCityCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-c-city">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.cCity.home.createLabel')">
                    Create a new C City
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
        <div class="alert alert-warning" v-if="!isFetching && cCities && cCities.length === 0">
            <span v-text="$t('opusWebApp.cCity.home.notFound')">No cCities found</span>
        </div>
        <div class="table-responsive" v-if="cCities && cCities.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.cCity.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.cCity.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('countryId')"><span v-text="$t('opusWebApp.cCity.country')">Country</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('regionId')"><span v-text="$t('opusWebApp.cCity.region')">Region</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'regionId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cCity in cCities"
                    :key="cCity.id">
                    <td>
                        <router-link :to="{name: 'CCityView', params: {cCityId: cCity.id}}">{{cCity.id}}</router-link>
                    </td>
                    <td>{{cCity.name}}</td>
                    <td>{{cCity.active}}</td>
                    <td>
                        <div v-if="cCity.countryId">
                            <router-link :to="{name: 'CCountryView', params: {cCountryId: cCity.countryId}}">{{cCity.countryId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="cCity.regionId">
                            <router-link :to="{name: 'CRegionView', params: {cRegionId: cCity.regionId}}">{{cCity.regionId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CCityView', params: {cCityId: cCity.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CCityEdit', params: {cCityId: cCity.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cCity)"
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
            <span slot="modal-title"><span id="opusWebApp.cCity.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cCity-heading" v-text="$t('opusWebApp.cCity.delete.question', {'id': removeId})">Are you sure you want to delete this C City?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cCity" v-text="$t('entity.action.delete')" v-on:click="removeCCity()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cCities && cCities.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-city.component.ts">
</script>
