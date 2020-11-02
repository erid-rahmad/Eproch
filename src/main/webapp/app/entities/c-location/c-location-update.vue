<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cLocation.home.createOrEditLabel" v-text="$t('opusWebApp.cLocation.home.createOrEditLabel')">Create or edit a CLocation</h2>
                <div>
                    <div class="form-group" v-if="cLocation.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cLocation.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cLocation.streetAddress')" for="c-location-streetAddress">Street Address</label>
                        <input type="text" class="form-control" name="streetAddress" id="c-location-streetAddress"
                            :class="{'valid': !$v.cLocation.streetAddress.$invalid, 'invalid': $v.cLocation.streetAddress.$invalid }" v-model="$v.cLocation.streetAddress.$model"  required/>
                        <div v-if="$v.cLocation.streetAddress.$anyDirty && $v.cLocation.streetAddress.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cLocation.streetAddress.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cLocation.postalCode')" for="c-location-postalCode">Postal Code</label>
                        <input type="text" class="form-control" name="postalCode" id="c-location-postalCode"
                            :class="{'valid': !$v.cLocation.postalCode.$invalid, 'invalid': $v.cLocation.postalCode.$invalid }" v-model="$v.cLocation.postalCode.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cLocation.taxInvoiceAddress')" for="c-location-taxInvoiceAddress">Tax Invoice Address</label>
                        <input type="checkbox" class="form-check" name="taxInvoiceAddress" id="c-location-taxInvoiceAddress"
                            :class="{'valid': !$v.cLocation.taxInvoiceAddress.$invalid, 'invalid': $v.cLocation.taxInvoiceAddress.$invalid }" v-model="$v.cLocation.taxInvoiceAddress.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cLocation.active')" for="c-location-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-location-active"
                            :class="{'valid': !$v.cLocation.active.$invalid, 'invalid': $v.cLocation.active.$invalid }" v-model="$v.cLocation.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cLocation.city')" for="c-location-city">City</label>
                        <select class="form-control" id="c-location-city" name="city" v-model="$v.cLocation.cityId.$model" required>
                            <option v-if="!cLocation.cityId" v-bind:value="null" selected></option>
                            <option v-bind:value="cCityOption.id" v-for="cCityOption in cCities" :key="cCityOption.id">{{cCityOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.cLocation.cityId.$anyDirty && $v.cLocation.cityId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.cLocation.cityId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cLocation.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-location-update.component.ts">
</script>
