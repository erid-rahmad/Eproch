<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.personInCharge.home.title')" id="person-in-charge-heading">Person In Charges</span>
            <router-link :to="{name: 'PersonInChargeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-person-in-charge">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.personInCharge.home.createLabel')">
                    Create a new Person In Charge
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
        <div class="alert alert-warning" v-if="!isFetching && personInCharges && personInCharges.length === 0">
            <span v-text="$t('opusWebApp.personInCharge.home.notFound')">No personInCharges found</span>
        </div>
        <div class="table-responsive" v-if="personInCharges && personInCharges.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('position')"><span v-text="$t('opusWebApp.personInCharge.position')">Position</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'position'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('opusWebApp.personInCharge.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('userLogin')"><span v-text="$t('opusWebApp.personInCharge.user')">User</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userLogin'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vendorId')"><span v-text="$t('opusWebApp.personInCharge.vendor')">Vendor</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vendorId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="personInCharge in personInCharges"
                    :key="personInCharge.id">
                    <td>
                        <router-link :to="{name: 'PersonInChargeView', params: {personInChargeId: personInCharge.id}}">{{personInCharge.id}}</router-link>
                    </td>
                    <td>{{personInCharge.position}}</td>
                    <td>{{personInCharge.phone}}</td>
                    <td>
                        {{personInCharge.userLogin}}
                    </td>
                    <td>
                        <div v-if="personInCharge.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: personInCharge.vendorId}}">{{personInCharge.vendorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PersonInChargeView', params: {personInChargeId: personInCharge.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PersonInChargeEdit', params: {personInChargeId: personInCharge.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(personInCharge)"
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
            <span slot="modal-title"><span id="opusWebApp.personInCharge.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-personInCharge-heading" v-text="$t('opusWebApp.personInCharge.delete.question', {'id': removeId})">Are you sure you want to delete this Person In Charge?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-personInCharge" v-text="$t('entity.action.delete')" v-on:click="removePersonInCharge()">Delete</button>
            </div>
        </b-modal>
        <div v-show="personInCharges && personInCharges.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./person-in-charge.component.ts">
</script>
