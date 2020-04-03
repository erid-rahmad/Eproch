<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.companyFunctionary.home.title')" id="company-functionary-heading">Company Functionaries</span>
            <router-link :to="{name: 'CompanyFunctionaryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-company-functionary">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.companyFunctionary.home.createLabel')">
                    Create a new Company Functionary
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
        <div class="alert alert-warning" v-if="!isFetching && companyFunctionaries && companyFunctionaries.length === 0">
            <span v-text="$t('opusWebApp.companyFunctionary.home.notFound')">No companyFunctionaries found</span>
        </div>
        <div class="table-responsive" v-if="companyFunctionaries && companyFunctionaries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.companyFunctionary.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('position')"><span v-text="$t('opusWebApp.companyFunctionary.position')">Position</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'position'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('opusWebApp.companyFunctionary.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('opusWebApp.companyFunctionary.email')">Email</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vendorId')"><span v-text="$t('opusWebApp.companyFunctionary.vendor')">Vendor</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vendorId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="companyFunctionary in companyFunctionaries"
                    :key="companyFunctionary.id">
                    <td>
                        <router-link :to="{name: 'CompanyFunctionaryView', params: {companyFunctionaryId: companyFunctionary.id}}">{{companyFunctionary.id}}</router-link>
                    </td>
                    <td>{{companyFunctionary.name}}</td>
                    <td>{{companyFunctionary.position}}</td>
                    <td>{{companyFunctionary.phone}}</td>
                    <td>{{companyFunctionary.email}}</td>
                    <td>
                        <div v-if="companyFunctionary.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: companyFunctionary.vendorId}}">{{companyFunctionary.vendorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompanyFunctionaryView', params: {companyFunctionaryId: companyFunctionary.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompanyFunctionaryEdit', params: {companyFunctionaryId: companyFunctionary.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(companyFunctionary)"
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
            <span slot="modal-title"><span id="opusWebApp.companyFunctionary.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-companyFunctionary-heading" v-text="$t('opusWebApp.companyFunctionary.delete.question', {'id': removeId})">Are you sure you want to delete this Company Functionary?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-companyFunctionary" v-text="$t('entity.action.delete')" v-on:click="removeCompanyFunctionary()">Delete</button>
            </div>
        </b-modal>
        <div v-show="companyFunctionaries && companyFunctionaries.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./company-functionary.component.ts">
</script>
