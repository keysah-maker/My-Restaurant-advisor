<?php

namespace App\Http\Controllers;

use App\Http\Resources\ReviewResource;
use App\Models\Menu;
use App\Models\Restaurant;
use App\Models\Review;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\AnonymousResourceCollection;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;

class ReviewController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @param Restaurant $id
     * @return AnonymousResourceCollection
     */
    public function index(Restaurant $id)
    {
        return ReviewResource::collection($id->reviews()->get());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Restaurant $id
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Restaurant $id, Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_id' => 'required|exists:users,id',
            'user_login' => 'required|exists:users,login',
            'rate' => 'required',
            'review' => 'required'
        ]);

        if ($validator->fails()) {
            return Response::json('Bad Request', 400);
        }

        $var = new Review([
            'user_id' => $request->get('user_id'),
            'user_login' => $request->get('user_login'),
            'restaurant_id' => $id->getKey(),
            'rate' => $request->get('rate'),
            'review' => $request->get('review')
        ]);

        $var->save();

        return Response::json('Created', 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param Restaurant $id
     * @param $review_id
     * @return JsonResponse
     */
    public function destroy($id, $review_id)
    {
        $review = Review::where([['restaurant_id', $id], ['id', $review_id]])->first();

        if (!$review)
            return Response::json('Bad Request', 400);


        if ($review->delete())
            return Response::json('OK', 200);

        return Response::json('Bad Request', 400);
    }
}
