<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.city.home.createOrEditLabel" v-text="$t('opusWebApp.city.home.createOrEditLabel')">Create or edit a City</h2>
                <div>
                    <div class="form-group" v-if="city.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="city.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.city.name')" for="city-name">Name</label>
                        <input type="text" class="form-control" name="name" id="city-name"
                            :class="{'valid': !$v.city.name.$invalid, 'invalid': $v.city.name.$invalid }" v-model="$v.city.name.$model"  required/>
                        <div v-if="$v.city.name.$anyDirty && $v.city.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.city.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.city.country')" for="city-country">Country</label>
                        <select class="form-control" id="city-country" name="country" v-model="city.countryId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="countryOption.id" v-for="countryOption in countries" :key="countryOption.id">{{countryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.city.region')" for="city-region">Region</label>
                        <select class="form-control" id="city-region" name="region" v-model="city.regionId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="regionOption.id" v-for="regionOption in regions" :key="regionOption.id">{{regionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.city.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./city-update.component.ts">
</script>
