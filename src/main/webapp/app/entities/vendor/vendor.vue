<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.vendor.home.title')" id="vendor-heading">Vendors</span>
            <router-link :to="{name: 'VendorCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-vendor">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.vendor.home.createLabel')">
                    Create a new Vendor
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
        <div class="alert alert-warning" v-if="!isFetching && vendors && vendors.length === 0">
            <span v-text="$t('opusWebApp.vendor.home.notFound')">No vendors found</span>
        </div>
        <div class="table-responsive" v-if="vendors && vendors.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('opusWebApp.vendor.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.vendor.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('npwp')"><span v-text="$t('opusWebApp.vendor.npwp')">Npwp</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'npwp'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('branch')"><span v-text="$t('opusWebApp.vendor.branch')">Branch</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'branch'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('opusWebApp.vendor.email')">Email</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('opusWebApp.vendor.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fax')"><span v-text="$t('opusWebApp.vendor.fax')">Fax</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fax'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('website')"><span v-text="$t('opusWebApp.vendor.website')">Website</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'website'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('type')"><span v-text="$t('opusWebApp.vendor.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('paymentCategory')"><span v-text="$t('opusWebApp.vendor.paymentCategory')">Payment Category</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'paymentCategory'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('approvalStatus')"><span v-text="$t('opusWebApp.vendor.approvalStatus')">Approval Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'approvalStatus'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('locationId')"><span v-text="$t('opusWebApp.vendor.location')">Location</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="vendor in vendors"
                    :key="vendor.id">
                    <td>
                        <router-link :to="{name: 'VendorView', params: {vendorId: vendor.id}}">{{vendor.id}}</router-link>
                    </td>
                    <td>{{vendor.code}}</td>
                    <td>{{vendor.name}}</td>
                    <td>{{vendor.npwp}}</td>
                    <td>{{vendor.branch}}</td>
                    <td>{{vendor.email}}</td>
                    <td>{{vendor.phone}}</td>
                    <td>{{vendor.fax}}</td>
                    <td>{{vendor.website}}</td>
                    <td v-text="$t('opusWebApp.VendorType.' + vendor.type)">{{vendor.type}}</td>
                    <td v-text="$t('opusWebApp.PaymentCategory.' + vendor.paymentCategory)">{{vendor.paymentCategory}}</td>
                    <td v-text="$t('opusWebApp.VendorApprovalStatus.' + vendor.approvalStatus)">{{vendor.approvalStatus}}</td>
                    <td>
                        <div v-if="vendor.locationId">
                            <router-link :to="{name: 'LocationView', params: {locationId: vendor.locationId}}">{{vendor.locationId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'VendorView', params: {vendorId: vendor.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'VendorEdit', params: {vendorId: vendor.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(vendor)"
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
            <span slot="modal-title"><span id="opusWebApp.vendor.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-vendor-heading" v-text="$t('opusWebApp.vendor.delete.question', {'id': removeId})">Are you sure you want to delete this Vendor?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-vendor" v-text="$t('entity.action.delete')" v-on:click="removeVendor()">Delete</button>
            </div>
        </b-modal>
        <div v-show="vendors && vendors.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./vendor.component.ts">
</script>
