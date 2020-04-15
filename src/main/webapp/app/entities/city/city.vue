<template>
    <div class="app-container">
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.city.home.title')" id="city-heading">City</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    circle
                    :title="$t('opusWebApp.city.home.createLabel')"
                    @click="add()"
                />
            </div>
            <el-table
                ref="mainTable"
                highlight-current-row
                max-height="512px"
                style="width: 100%"
                @sort-change="changeOrder"
                :data="cities"
                :default-sort="{prop: 'name', order: 'ascending'}"
                :empty-text="$t('opusWebApp.city.home.notFound')"
                @current-change="changeClassificationSelection"
            >
                <el-table-column
                    prop="name"
                    sortable
                    :label="$t('opusWebApp.city.name')"
                />
                <el-table-column
                    prop="countryId"
                    sortable
                    :label="$t('opusWebApp.city.country')"
                />
                <el-table-column
                    prop="regionId"
                    sortable
                    :label="$t('opusWebApp.city.region')"
                />
                <el-table-column
                    label="Operations"
                    width="200"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="openDetails(scope.row)"
                            type="warning"
                            size="mini"
                            icon="el-icon-info"
                            plain
                            :title="$t('entity.action.view')"
                        />
                        <el-button
                            @click="edit(scope.row)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="loadPage"
                :current-page.sync="page"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="itemsPerPage"
                layout="total, sizes, prev, pager, next, jumper"
                :total="queryCount">
            </el-pagination>
        </div>
        <el-dialog
            width="30%"
            :title="$t('entity.delete.title')"
            :visible="showDeleteDialog"
        >
            <span>{{ $t('opusWebApp.city.delete.question', {'id': removeId}) }}</span>
            <div slot="footer">
                <el-button icon="el-icon-close" @click="closeDialog()">{{ $t('entity.action.cancel') }}</el-button>
                <el-button icon="el-icon-delete" type="danger" @click="removeCity()">{{ $t('entity.action.delete') }}</el-button>
            </div>
        </el-dialog>
    </div>


<!--
    <div>
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.city.home.title')" id="city-heading">Cities</span>
            <router-link :to="{name: 'CityCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-city">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('opusWebApp.city.home.createLabel')">
                    Create a new City
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
        <div class="alert alert-warning" v-if="!isFetching && cities && cities.length === 0">
            <span v-text="$t('opusWebApp.city.home.notFound')">No cities found</span>
        </div>
        <div class="table-responsive" v-if="cities && cities.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('opusWebApp.city.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('countryId')"><span v-text="$t('opusWebApp.city.country')">Country</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('regionId')"><span v-text="$t('opusWebApp.city.region')">Region</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'regionId'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="city in cities"
                    :key="city.id">
                    <td>
                        <router-link :to="{name: 'CityView', params: {cityId: city.id}}">{{city.id}}</router-link>
                    </td>
                    <td>{{city.name}}</td>
                    <td>
                        <div v-if="city.countryId">
                            <router-link :to="{name: 'CountryView', params: {countryId: city.countryId}}">{{city.countryId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="city.regionId">
                            <router-link :to="{name: 'RegionView', params: {regionId: city.regionId}}">{{city.regionId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CityView', params: {cityId: city.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CityEdit', params: {cityId: city.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(city)"
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
            <span slot="modal-title"><span id="opusWebApp.city.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-city-heading" v-text="$t('opusWebApp.city.delete.question', {'id': removeId})">Are you sure you want to delete this City?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-city" v-text="$t('entity.action.delete')" v-on:click="removeCity()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cities && cities.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
    -->
</template>

<script lang="ts" src="./city.component.ts">
</script>
