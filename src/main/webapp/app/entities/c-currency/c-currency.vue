<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.cCurrency.home.title')" id="c-currency-heading">C Currencies</span>
            <router-link :to="{name: 'CCurrencyCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-c-currency">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.cCurrency.home.createLabel')">
                    Create a new C Currency
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
        <div class="alert alert-warning" v-if="!isFetching && cCurrencies && cCurrencies.length === 0">
            <span v-text="$t('opusWebApp.cCurrency.home.notFound')">No cCurrencies found</span>
        </div>
        <div class="table-responsive" v-if="cCurrencies && cCurrencies.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('opusWebApp.cCurrency.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('symbol')"><span v-text="$t('opusWebApp.cCurrency.symbol')">Symbol</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'symbol'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.cCurrency.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.cCurrency.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cCurrency in cCurrencies"
                    :key="cCurrency.id">
                    <td>
                        <router-link :to="{name: 'CCurrencyView', params: {cCurrencyId: cCurrency.id}}">{{cCurrency.id}}</router-link>
                    </td>
                    <td>{{cCurrency.code}}</td>
                    <td>{{cCurrency.symbol}}</td>
                    <td>{{cCurrency.name}}</td>
                    <td>{{cCurrency.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CCurrencyView', params: {cCurrencyId: cCurrency.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CCurrencyEdit', params: {cCurrencyId: cCurrency.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cCurrency)"
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
            <span slot="modal-title"><span id="opusWebApp.cCurrency.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cCurrency-heading" v-text="$t('opusWebApp.cCurrency.delete.question', {'id': removeId})">Are you sure you want to delete this C Currency?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cCurrency" v-text="$t('entity.action.delete')" v-on:click="removeCCurrency()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cCurrencies && cCurrencies.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-currency.component.ts">
</script>
