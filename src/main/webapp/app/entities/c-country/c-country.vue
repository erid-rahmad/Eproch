<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.cCountry.home.title')" id="c-country-heading">C Countries</span>
            <router-link :to="{name: 'CCountryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-c-country">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.cCountry.home.createLabel')">
                    Create a new C Country
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
        <div class="alert alert-warning" v-if="!isFetching && cCountries && cCountries.length === 0">
            <span v-text="$t('opusWebApp.cCountry.home.notFound')">No cCountries found</span>
        </div>
        <div class="table-responsive" v-if="cCountries && cCountries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.cCountry.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('opusWebApp.cCountry.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('withRegion')"><span v-text="$t('opusWebApp.cCountry.withRegion')">With Region</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'withRegion'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.cCountry.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('currencyId')"><span v-text="$t('opusWebApp.cCountry.currency')">Currency</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'currencyId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cCountry in cCountries"
                    :key="cCountry.id">
                    <td>
                        <router-link :to="{name: 'CCountryView', params: {cCountryId: cCountry.id}}">{{cCountry.id}}</router-link>
                    </td>
                    <td>{{cCountry.name}}</td>
                    <td>{{cCountry.code}}</td>
                    <td>{{cCountry.withRegion}}</td>
                    <td>{{cCountry.active}}</td>
                    <td>
                        <div v-if="cCountry.currencyId">
                            <router-link :to="{name: 'CCurrencyView', params: {cCurrencyId: cCountry.currencyId}}">{{cCountry.currencyId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CCountryView', params: {cCountryId: cCountry.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CCountryEdit', params: {cCountryId: cCountry.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cCountry)"
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
            <span slot="modal-title"><span id="opusWebApp.cCountry.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cCountry-heading" v-text="$t('opusWebApp.cCountry.delete.question', {'id': removeId})">Are you sure you want to delete this C Country?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cCountry" v-text="$t('entity.action.delete')" v-on:click="removeCCountry()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cCountries && cCountries.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-country.component.ts">
</script>
