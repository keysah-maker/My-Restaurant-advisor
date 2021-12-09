<?php

namespace App\Models;

use App\Http\Resources\RestaurantResource;
use Illuminate\Database\Eloquent\Model;

class Review extends Model
{
    protected $table = "reviews";

    protected $fillable = [
        'user_id', 'user_login', 'restaurant_id', 'rate', 'review'
    ];

    public function restaurants()
    {
        return $this->belongsTo(RestaurantResource::class);
    }
}
