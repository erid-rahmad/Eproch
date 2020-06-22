<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.cBank.home.title')" id="c-bank-heading">C Banks</span>
            <router-link :to="{name: 'CBankCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-c-bank">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.cBank.home.createLabel')">
                    Create a new C Bank
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
        <div class="alert alert-warning" v-if="!isFetching && cBanks && cBanks.length === 0">
            <span v-text="$t('opusWebApp.cBank.home.notFound')">No cBanks found</span>
        </div>
        <div class="table-responsive" v-if="cBanks && cBanks.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.cBank.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('opusWebApp.cBank.value')">Value</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'value'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('shortName')"><span v-text="$t('opusWebApp.cBank.shortName')">Short Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shortName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('opusWebApp.cBank.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('swiftCode')"><span v-text="$t('opusWebApp.cBank.swiftCode')">Swift Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'swiftCode'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('opusWebApp.cBank.active')">Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cBank in cBanks"
                    :key="cBank.id">
                    <td>
                        <router-link :to="{name: 'CBankView', params: {cBankId: cBank.id}}">{{cBank.id}}</router-link>
                    </td>
                    <td>{{cBank.name}}</td>
                    <td>{{cBank.value}}</td>
                    <td>{{cBank.shortName}}</td>
                    <td>{{cBank.description}}</td>
                    <td>{{cBank.swiftCode}}</td>
                    <td>{{cBank.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CBankView', params: {cBankId: cBank.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CBankEdit', params: {cBankId: cBank.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cBank)"
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
            <span slot="modal-title"><span id="opusWebApp.cBank.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cBank-heading" v-text="$t('opusWebApp.cBank.delete.question', {'id': removeId})">Are you sure you want to delete this C Bank?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cBank" v-text="$t('entity.action.delete')" v-on:click="removeCBank()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cBanks && cBanks.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-bank.component.ts">
</script>
