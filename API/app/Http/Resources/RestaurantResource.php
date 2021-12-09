<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class RestaurantResource extends JsonResource
{

    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */

    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'description' => $this->description,
            'grade' => $this->grade,
            'localization' => $this->localization,
            'phone_number' => $this->phone_number,
            'website' => $this->website,
            'hours' => $this->hours
        ];
    }
}
