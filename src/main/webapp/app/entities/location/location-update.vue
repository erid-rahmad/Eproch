<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.location.home.createOrEditLabel" v-text="$t('opusWebApp.location.home.createOrEditLabel')">Create or edit a Location</h2>
                <div>
                    <div class="form-group" v-if="location.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="location.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.streetAddress')" for="location-streetAddress">Street Address</label>
                        <input type="text" class="form-control" name="streetAddress" id="location-streetAddress"
                            :class="{'valid': !$v.location.streetAddress.$invalid, 'invalid': $v.location.streetAddress.$invalid }" v-model="$v.location.streetAddress.$model"  required/>
                        <div v-if="$v.location.streetAddress.$anyDirty && $v.location.streetAddress.$invalid">
                            <small class="form-text text-danger" v-if="!$v.location.streetAddress.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.postalCode')" for="location-postalCode">Postal Code</label>
                        <input type="text" class="form-control" name="postalCode" id="location-postalCode"
                            :class="{'valid': !$v.location.postalCode.$invalid, 'invalid': $v.location.postalCode.$invalid }" v-model="$v.location.postalCode.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.city')" for="location-city">City</label>
                        <select class="form-control" id="location-city" name="city" v-model="$v.location.cityId.$model" required>
                            <option v-if="!location.cityId" v-bind:value="null" selected></option>
                            <option v-bind:value="cityOption.id" v-for="cityOption in cities" :key="cityOption.id">{{cityOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.location.cityId.$anyDirty && $v.location.cityId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.location.cityId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.vendor')" for="location-vendor">Vendor</label>
                        <select class="form-control" id="location-vendor" name="vendor" v-model="location.vendorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="vendorOption.id" v-for="vendorOption in vendors" :key="vendorOption.id">{{vendorOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.location.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./location-update.component.ts">
</script>
