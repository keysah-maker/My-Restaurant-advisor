<?php

namespace App\Models;

use App\Http\Resources\RestaurantResource;
use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    protected $table = "menus";

    protected $fillable = [
        'name', 'description', 'price', 'restaurant_id'
    ];

    public function restaurant()
    {
        return $this->belongsTo(RestaurantResource::class);
    }
}
