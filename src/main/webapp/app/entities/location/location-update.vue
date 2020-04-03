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
                            :class="{'valid': !$v.location.streetAddress.$invalid, 'invalid': $v.location.streetAddress.$invalid }" v-model="$v.location.streetAddress.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.postalCode')" for="location-postalCode">Postal Code</label>
                        <input type="number" class="form-control" name="postalCode" id="location-postalCode"
                            :class="{'valid': !$v.location.postalCode.$invalid, 'invalid': $v.location.postalCode.$invalid }" v-model.number="$v.location.postalCode.$model" />
                        <div v-if="$v.location.postalCode.$anyDirty && $v.location.postalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.location.postalCode.min" v-text="$t('entity.validation.min', {min: 5})">
                                This field should be at least 5.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.location.postalCode.max" v-text="$t('entity.validation.max', {max: 5})">
                                This field cannot be longer than 5 characters.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.location.postalCode.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.city')" for="location-city">City</label>
                        <input type="text" class="form-control" name="city" id="location-city"
                            :class="{'valid': !$v.location.city.$invalid, 'invalid': $v.location.city.$invalid }" v-model="$v.location.city.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.stateProvince')" for="location-stateProvince">State Province</label>
                        <input type="text" class="form-control" name="stateProvince" id="location-stateProvince"
                            :class="{'valid': !$v.location.stateProvince.$invalid, 'invalid': $v.location.stateProvince.$invalid }" v-model="$v.location.stateProvince.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.location.country')" for="location-country">Country</label>
                        <select class="form-control" id="location-country" name="country" v-model="$v.location.countryId.$model" required>
                            <option v-if="!location.countryId" v-bind:value="null" selected></option>
                            <option v-bind:value="countryOption.id" v-for="countryOption in countries" :key="countryOption.id">{{countryOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.location.countryId.$anyDirty && $v.location.countryId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.location.countryId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
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
