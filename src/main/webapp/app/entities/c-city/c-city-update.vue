<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cCity.home.createOrEditLabel" v-text="$t('opusWebApp.cCity.home.createOrEditLabel')">Create or edit a CCity</h2>
                <div>
                    <div class="form-group" v-if="cCity.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cCity.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCity.name')" for="c-city-name">Name</label>
                        <input type="text" class="form-control" name="name" id="c-city-name"
                            :class="{'valid': !$v.cCity.name.$invalid, 'invalid': $v.cCity.name.$invalid }" v-model="$v.cCity.name.$model"  required/>
                        <div v-if="$v.cCity.name.$anyDirty && $v.cCity.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cCity.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCity.active')" for="c-city-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-city-active"
                            :class="{'valid': !$v.cCity.active.$invalid, 'invalid': $v.cCity.active.$invalid }" v-model="$v.cCity.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCity.country')" for="c-city-country">Country</label>
                        <select class="form-control" id="c-city-country" name="country" v-model="cCity.countryId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cCountryOption.id" v-for="cCountryOption in cCountries" :key="cCountryOption.id">{{cCountryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cCity.region')" for="c-city-region">Region</label>
                        <select class="form-control" id="c-city-region" name="region" v-model="cCity.regionId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cRegionOption.id" v-for="cRegionOption in cRegions" :key="cRegionOption.id">{{cRegionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cCity.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-city-update.component.ts">
</script>
